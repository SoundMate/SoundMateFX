package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.*;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JoinRequestDaoTest {

    private static final UserDao userDao = new UserDao();
    private final BandDao bandDao = new BandDao(userDao);
    private final SoloDao soloDao = new SoloDao(userDao);
    private static final ApplicationDao applicationDao = new ApplicationDao();
    private static final JoinRequestDao sut = new JoinRequestDao();

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
    void cancelRequestTest(){
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
    void acceptRequestTest(){
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



    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
        applicationDao.deleteApplications();
        sut.deleteJoinRequests();

    }



}
