package it.soundmate.database.dao;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import it.soundmate.model.RequestState;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationDao {

    public static final String INSTRUMENTS = "instruments";
    private final Connector connector = Connector.getInstance();
    private static final String ERROR = "Error updating entry. The error was: \n";

    //crud
    //the band post an application
    public Application createApplication(Application application) {
        String sql = "insert into applications (id_band, message, instruments) values (?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1, application.getBandId());
            preparedStatement.setString(2, application.getMessage());
            preparedStatement.setArray(3, conn.createArrayOf("text", application.getInstrumentsList().toArray()));

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return application.withCode(resultSet.getInt("code"));
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error inserting entry: the error was: \n" + ex.getMessage(), ex);
        } return application.withCode(-1); //invalid code, abort (must be > 0)
    }

    //ritorna tutte le application "aperte" da una band (se una, ritorna una lista di un elemento)
    public List<Application> getApplicationByBandId(int bandId) {
        String sql = "SELECT code, message, instruments FROM applications where id_band = ?";
        List<Application> applicationsList = new ArrayList<>();

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, bandId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();

                application.setApplicationCode(resultSet.getInt("code"));
                application.setMessage(resultSet.getString("message"));

                String[] instruments = (String[]) resultSet.getArray(INSTRUMENTS).getArray();
                List<String> instrumentsList = Arrays.asList(instruments);
                application.setInstrumentsList(instrumentsList);
                application.setAppliedSoloList(this.getSolosApplied(application));
                application.setJoinRequestList(this.getJoinRequests(application));
                applicationsList.add(application);
            }
            return applicationsList;

        } catch (SQLException ex) {
            throw new RepositoryException("Error fetching applications. The error was: \n" + ex.getMessage(), ex);
        }
    }


    //the band wants to add an instrument requested in the application. This is the method:
    public boolean updateApplicationInstrument(String instrument, int applicationCode){
        String sql = "update applications set instruments = array_append(instruments, ?::text) where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setString(1, instrument);
            preparedStatement.setInt(2, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    //the band wants to change the message attached to the application. This is the method:
    public boolean updateApplicationMessage(String message, int applicationCode){
        String sql = "update applications set message = ? where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    public List<SoloResultBean> getSolosApplied(Application application) {
        String sql = "SELECT s.id, ru.email, ru.password, s.last_name, s.first_name, pi.instruments, u.encoded_profile_img, ru.city\n" +
                "FROM applications " +
                "JOIN join_request jr on applications.code = jr.code_application\n" +
                "JOIN solo s on jr.id_solo = s.id\n" +
                "JOIN played_instruments pi on s.id = pi.id "+
                "JOIN registered_users ru on ru.id = s.id\n" +
                "JOIN users u on u.id = s.id\n" +
                "WHERE applications.code = ?";
        List<SoloResultBean> solosList = new ArrayList<>();
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, application.getApplicationCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SoloResultBean soloResultBean = buildSoloResultBeans(resultSet);
                if (resultSet.getArray(INSTRUMENTS) != null){
                    List<String> instrumentList;
                    String [] temp = (String []) resultSet.getArray(INSTRUMENTS).getArray();
                    instrumentList = Arrays.asList(temp);
                    soloResultBean.setInstrumentList(instrumentList);
                }
                solosList.add(soloResultBean);
            }
            application.setAppliedSoloList(solosList);
            return solosList;

        } catch (SQLException ex){
            throw new RepositoryException("Error fetching solos. The error was: \n" + ex.getMessage(), ex);
        }
    }

    public static SoloResultBean buildSoloResultBeans(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String city = resultSet.getString("city");
        String email = resultSet.getString("email");
        String encodedImg = resultSet.getString("encoded_profile_img");
        int id = resultSet.getInt("id");
        return new SoloResultBean(id, email, encodedImg, firstName, lastName, city);
    }


    //the band wants to delete the application.
    public boolean deleteApplicationByCode(int applicationCode) {
        String sql = "delete from applications where code = ?";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            throw new RepositoryException("Error deleting entry. The error was: \n" + ex.getMessage(), ex);
        }
    }


    public List<JoinRequest> getJoinRequests(Application application) {
        String sql = "SELECT * from join_request where code_application = (?)";
        JoinRequestDao joinRequestDao = new JoinRequestDao();
        List<JoinRequest> joinRequestList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, application.getApplicationCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int requestCode = resultSet.getInt("code");
                int idBand = resultSet.getInt("id_band");
                int idSolo = resultSet.getInt("id_solo");
                String message = resultSet.getString("message");
                String requestState = resultSet.getString("request_state");
                JoinRequest joinRequest = new JoinRequest(idBand, application.getApplicationCode(), idSolo, message);
                joinRequest.setCode(requestCode);
                joinRequest.setRequestState(RequestState.returnRequestState(requestState));
                joinRequest.setSoloResultBean(joinRequestDao.getSoloFromJoinRequest(joinRequest));
                joinRequestList.add(joinRequest);
            }
            return joinRequestList;
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }
}
