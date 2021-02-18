package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.*;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import it.soundmate.model.RequestState;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JoinRequestDaoTest {

    private final Connector connector = Connector.getInstance();
    private static final UserDao userDao = new UserDao();
    private final BandDao bandDao = new BandDao(userDao);
    private final SoloDao soloDao = new SoloDao(userDao);
    private static final ApplicationDao applicationDao = new ApplicationDao();
    private static final JoinRequestDao sut = new JoinRequestDao();

    @AfterAll
    static void tearDown() {
        userDao.deleteAll();
        PowerUserDao powerUserDao = new PowerUserDao();
        powerUserDao.delete("DELETE FROM applications", "ALTER SEQUENCE applications_code_seq RESTART WITH 1");
        powerUserDao.delete("DELETE FROM join_request", "ALTER SEQUENCE join_request_code_seq RESTART WITH 1");

    }

    @Test
    @Order(1)
    void sendRequestTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("pluto@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("pippo@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jr = new JoinRequest(bandId, application.getApplicationCode(), soloId, "s'allebba la pampuja");

        Assertions.assertEquals(1, sut.sendRequestToApplication(application, jr).getCodeApplication());

    }

    @Test
    @Order(2)
    void cancelRequestTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("band@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jrNoCode = new JoinRequest(bandId, application.getApplicationCode(), soloId, "s'allebba la pampuja");
        JoinRequest jrWithCode = sut.sendRequestToApplication(application, jrNoCode);

        Assertions.assertTrue(sut.cancelRequest(jrWithCode));
    }

    @Test
    @Order(3)
    void acceptRequestTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("band2@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo2@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jrNoCode = new JoinRequest(bandId, application.getApplicationCode(), soloId, "s'allebba la pampuja");
        JoinRequest jrWithCode = sut.sendRequestToApplication(application, jrNoCode);

        Assertions.assertTrue(sut.acceptRequest(jrWithCode));

    }

    @Test
    @Order(4)
    void rejectRequestTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("band3@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo3@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jrNoCode = new JoinRequest(bandId, application.getApplicationCode(), soloId, "s'allebba la pampuja");
        JoinRequest jrWithCode = sut.sendRequestToApplication(application, jrNoCode);

        Assertions.assertTrue(sut.rejectRequest(jrWithCode));

    }

    @Test
    @Order(5)
    void getJoinRequestByApplicationCodeTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("band4@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo4@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jrNoCode = new JoinRequest(bandId, application.getApplicationCode(), soloId, "joinRequestTest");
        JoinRequest jrWithCode = sut.sendRequestToApplication(application, jrNoCode);

        JoinRequest jrNoCode2 = new JoinRequest(bandId, application.getApplicationCode(), soloId, "joinRequestTest2");
        JoinRequest jrWithCode2 = sut.sendRequestToApplication(application, jrNoCode2);

        JoinRequest jrNoCode3 = new JoinRequest(bandId, application.getApplicationCode(), soloId, "joinRequestTest3");
        JoinRequest jrWithCode3 = sut.sendRequestToApplication(application, jrNoCode3);

        JoinRequest jrNoCode4 = new JoinRequest(bandId, application.getApplicationCode(), soloId, "joinRequestTest4");
        JoinRequest jrWithCode4 = sut.sendRequestToApplication(application, jrNoCode4);

        Assertions.assertEquals(4, sut.getJoinRequestsByApplicationCode(application).size());

    }

    @Test
    @Order(6)
    void requestStateTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("band5@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo5@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, "drums", "ciao, sono pippo");
        application = applicationDao.createApplication(application);

        JoinRequest jrNoCode = new JoinRequest(bandId, application.getApplicationCode(), soloId, "joinRequestTest");
        JoinRequest jrWithCode = sut.sendRequestToApplication(application, jrNoCode);

        Assertions.assertEquals(RequestState.CREATED, getStateForTest(jrWithCode));
    }


    RequestState getStateForTest(JoinRequest joinRequest){
        String sql = "SELECT request_state FROM join_request WHERE code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, joinRequest.getCode());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return RequestState.returnRequestState(resultSet.getString("request_state"));
            }


        return null;
        }catch (SQLException ex){
            throw new RepositoryException("ERROR: \n" + ex.getMessage(), ex);
        }
    }



}
