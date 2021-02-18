package it.soundmate.database.dao;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import it.soundmate.model.MessageType;
import it.soundmate.model.RequestState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String sql = "update join_request set request_state = ? where code = ?";
        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, RequestState.ACCEPTED.toString());
            preparedStatement.setInt(2, joinRequest.getCode());
            preparedStatement.executeUpdate();
            this.notifySolo(joinRequest.getIdBand(), joinRequest.getIdSolo(), MessageType.JOIN_BAND_CONFIRMATION, joinRequest.getCode());
            return true;
        }catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    public boolean rejectRequest(JoinRequest joinRequest){
        String sql = "update join_request set request_state = ? where code = ?";
        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, RequestState.REJECTED.toString());
            preparedStatement.setInt(2, joinRequest.getCode());
            preparedStatement.executeUpdate();
            this.notifySolo(joinRequest.getIdBand(), joinRequest.getIdSolo(), MessageType.JOIN_BAND_CANCELED, joinRequest.getCode());
            return true;
        }catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    private void notifySolo(int idBand, int idSolo, MessageType type, int joinID) {
        String sql="insert into notifications (sender, receiver, type, seen, join_request) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, idBand);
            preparedStatement.setInt(2, idSolo);
            preparedStatement.setString(3, type.toString());
            preparedStatement.setBoolean(4, false);
            preparedStatement.setInt(5, joinID);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new RepositoryException(exception.getMessage());
        }
    }


    public List<JoinRequest> getJoinRequestsByApplicationCode(Application application){
        String sql = "SELECT jr.code, jr.id_band, jr.id_solo, jr.message, jr.request_state " +
                     "from applications " +
                     "JOIN join_request jr on applications.code = jr.code_application " +
                     "WHERE applications.code = ?";

        List<JoinRequest> joinRequestList = new ArrayList<>();

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, application.getApplicationCode());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JoinRequest joinRequest = new JoinRequest();
                joinRequest.setCode(resultSet.getInt("code"));
                joinRequest.setMessage(resultSet.getString("message"));
                joinRequest.setIdBand(resultSet.getInt("id_band"));
                joinRequest.setIdSolo(resultSet.getInt("id_solo"));
                joinRequest.setRequestState(RequestState.returnRequestState(resultSet.getString("request_state")));
                joinRequest.setCodeApplication(application.getApplicationCode());
                joinRequestList.add(joinRequest);
            }
            return joinRequestList;

        }catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
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


    public SoloResultBean getSoloFromJoinRequest(JoinRequest joinRequest) {
        String sql = "SELECT * from join_request join solo s on s.id = join_request.id_solo join users u on u.id = s.id join registered_users ru on ru.id = u.id join played_instruments pi on s.id = pi.id where code = (?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

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
        SoloResultBean soloResultBean = ApplicationDao.buildSoloResultBeans(resultSet);
        if (resultSet.getArray("instruments") != null){
            List<String> instrumentList;
            String [] temp = (String []) resultSet.getArray("instruments").getArray();
            instrumentList = Arrays.asList(temp);
            soloResultBean.setInstrumentList(instrumentList);
        }
        return soloResultBean;
    }

    public JoinRequest getJoinRequestByID(int requestID) throws SQLException {
        log.info("Getting join request id: {}", requestID);
        String sql = "SELECT * from join_request where code = (?)";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, requestID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idBand = resultSet.getInt("id_band");
                int idSolo = resultSet.getInt("id_solo");
                String message = resultSet.getString("message");
                int applicationCode = resultSet.getInt("code_application");
                BandDao bandDao = new BandDao(new UserDao());
                BandResultBean bandResultBean = bandDao.getBandName(idBand);
                log.info("get Join Request by ID: band name {}", bandResultBean.getBandName());
                JoinRequest joinRequest = new JoinRequest(idBand, applicationCode, idSolo, message);
                joinRequest.setBand(bandResultBean);
                return joinRequest;
            }
        }
        return null;
    }
}
