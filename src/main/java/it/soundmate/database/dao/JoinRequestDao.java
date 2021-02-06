package it.soundmate.database.dao;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class JoinRequestDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(JoinRequestDao.class);
    private static final String ERROR = "Error updating entry. The error was: \n";



    //when a solo user send request for application, it creates the entry for JoinRequest tab.


    public JoinRequest sendRequestToApplication(Application application, JoinRequest joinRequest){
        String sql = "INSERT INTO join_request (code_application, id_band, id_solo, message) values (?, ?, ?, ?)";


        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1, application.getApplicationCode());
            preparedStatement.setInt(2, joinRequest.getIdBand());
            preparedStatement.setInt(3, joinRequest.getIdSolo());
            preparedStatement.setString(4, joinRequest.getMessage());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return joinRequest.withCode(resultSet.getInt("code"));
                }
            }
            return joinRequest.withCode(-1);
        } catch (SQLException ex) {
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }


    public boolean acceptRequest(JoinRequest joinRequest){
        String sql = "update join_request set is_accepted = ? where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, joinRequest.getCode());

            return preparedStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RepositoryException("Error deleting entry. The error was: \n" + ex.getMessage(), ex);
        }
    }


    public boolean cancelRequest(JoinRequest joinRequest){
        String sql = "delete from join_request where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, joinRequest.getCode());

            return preparedStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RepositoryException("Error deleting entry. The error was: \n" + ex.getMessage(), ex);
        }
    }


    //testing purpose
    public void deleteJoinRequests() {
        String sql = "DELETE FROM join_request";
        int delRecs;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** join Request Entries Successfully Cleaned! *****");
            resetCode();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting Join Requests", ex);
        }
    }

    private void resetCode() {
        String sql = "ALTER SEQUENCE join_request_code_seq RESTART WITH 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** Code Values reset successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error ResetCode", ex);
        }
    }


    public SoloResultBean getSoloFromJoinRequest(JoinRequest joinRequest) {
        String sql = "SELECT * from join_request join solo s on s.id = join_request.id_solo join users u on u.id = s.id join registered_users ru on ru.id = u.id join played_instruments pi on s.id = pi.id where code = (?)";
        try (Connection conn = connector.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, joinRequest.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return buildSoloResultBean(resultSet);
            } else throw new RepositoryException("Error solo");

        } catch (SQLException ex){
            throw new RepositoryException("Error fetching solos. The error was: \n" + ex.getMessage(), ex);
        }
    }

    public static SoloResultBean buildSoloResultBean(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String city = resultSet.getString("city");
        String email = resultSet.getString("email");
        String encodedImg = resultSet.getString("encoded_profile_img");
        int id = resultSet.getInt("id");
        SoloResultBean soloResultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName, city);
        if (resultSet.getArray("instruments") != null){
            List<String> instrumentList;
            String [] temp = (String []) resultSet.getArray("instruments").getArray();
            instrumentList = Arrays.asList(temp);
            soloResultBean.setInstrumentList(instrumentList);
        }
        return soloResultBean;
    }

}
