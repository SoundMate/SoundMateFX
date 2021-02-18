package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.*;
import it.soundmate.model.Application;
import it.soundmate.model.JoinRequest;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationDaoTest {

    private static final ApplicationDao sut = new ApplicationDao();
    private static final UserDao userDao = new UserDao();
    private final BandDao bandDao = new BandDao(userDao);
    private final SoloDao soloDao = new SoloDao(userDao);
    private static final JoinRequestDao joinRequestDao = new JoinRequestDao();

    @Test
    @Order(1)
    void createEntryTest(){
        RegisterBandBean regBandBean = new RegisterBandBean("pluto@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        Application application = new Application(bandId,  "drums", "ciao, sono pippo");

        Assertions.assertEquals(1, sut.createApplication(application).getApplicationCode());
    }

    @Test
    @Order(2)
    void updateMessageTest(){
        String msg = "ciao questo è un test";
        RegisterBandBean regBandBean = new RegisterBandBean("DT@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        Application application = new Application(bandId, "drums", "ciao, vieni a suonare!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.updateApplicationMessage(msg, code));
    }



    @Test
    @Order(3)
    void updateInstrumentTest(){

        RegisterBandBean regBandBean = new RegisterBandBean("MM@", "asd", "MuteMath", "New Orleans");
        int bandId = bandDao.registerBand(regBandBean);

        Application application = new Application(bandId, "drums, guitar", "hello world!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.updateApplicationInstrument("piano", code));


    }

    @Test
    @Order(4)
    void deleteApplicationTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("pupo@", "asd", "pupi", "Roccacannuccia");
        int bandId = bandDao.registerBand(regBandBean);

        Application application = new Application(bandId,  "drums, guitar", "hello world!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.deleteApplicationByCode(code));
    }

    @Test
    @Order(5)
    void getApplicationsByBandIdTest(){
        RegisterBandBean regBandBean = new RegisterBandBean("gino@", "asd", "vannelli", "Toronto");
        int bandId = bandDao.registerBand(regBandBean);

        Application application1 = new Application(bandId,  "guitar", "hello!");
        sut.createApplication(application1);
        Application application2 = new Application(bandId,  "drums", "world!");
        sut.createApplication(application2);
        Application application3 = new Application(bandId, "drums, guitar", "hi!");
        sut.createApplication(application3);

        //la stessa band ha inserito 3 applications, mi aspetto 3 se faccio la size della lista ritornata.
        Assertions.assertEquals(3, sut.getApplicationByBandId(bandId).size());

    }

    @Test
    @Order(6)
    void getSolosAppliedTest(){

        RegisterBandBean regBandBean = new RegisterBandBean("plutonio@", "asd", "abbrath", "Paglieta");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("solo1@", "dsa", "tizio", "verdi", "Milan");
        int soloId = soloDao.registerSolo(regSoloBean);
        RegisterSoloBean regSoloBean2 = new RegisterSoloBean("solo2@", "asd", "sempronio", "neri", "Rome");
        int soloId2 = soloDao.registerSolo(regSoloBean2);
        RegisterSoloBean regSoloBean3 = new RegisterSoloBean("solo3@", "qwer", "caio", "pinco", "Florence");
        int soloId3 = soloDao.registerSolo(regSoloBean3);



        Application application = new Application(bandId, "guitar", "hello!");
        Application applicationWithCode = sut.createApplication(application);
        JoinRequest jr1 = new JoinRequest(bandId, applicationWithCode.getApplicationCode(), soloId, "ciao sono tizio");
        JoinRequest jr2 = new JoinRequest(bandId, applicationWithCode.getApplicationCode(), soloId2, "ciao sono sempronio");
        JoinRequest jr3 = new JoinRequest(bandId, applicationWithCode.getApplicationCode(), soloId3, "ciao sono caio");

        joinRequestDao.sendRequestToApplication(applicationWithCode, jr1 );
        joinRequestDao.sendRequestToApplication(applicationWithCode, jr2);
        joinRequestDao.sendRequestToApplication(applicationWithCode, jr3);

        Assertions.assertEquals(3, sut.getSolosApplied(applicationWithCode).size());

    }


    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
        PowerUserDao powerUserDao = new PowerUserDao();
        powerUserDao.delete("DELETE FROM applications", "ALTER SEQUENCE applications_code_seq RESTART WITH 1");
        powerUserDao.delete("DELETE FROM join_request", "ALTER SEQUENCE join_request_code_seq RESTART WITH 1");
    }



}
