package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.ApplicationDao;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Application;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationDaoTest {

    private final Connector connector = Connector.getInstance();
    private static final ApplicationDao sut = new ApplicationDao();
    private static final UserDao userDao = new UserDao();
    private final BandDao bandDao = new BandDao(userDao);
    private final SoloDao soloDao = new SoloDao(userDao);

    @Test
    @Order(1)
    void createEntryTest(){
        RegisterBandBean regBandBean = new RegisterBandBean("pluto@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("pippo@", "asd", "pippo", "pluto", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);
        Application application = new Application(bandId, soloId , "drums", "ciao, sono pippo");

        Assertions.assertEquals(1, sut.createApplication(application).getApplicationCode());
    }

    @Test
    @Order(2)
    void updateMessageTest(){
        String msg = "ciao questo è un test";
        RegisterBandBean regBandBean = new RegisterBandBean("DT@", "asd", "DT", "Boston");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("beppe@", "asd", "beppe", "rossi", "Rome");
        int soloId = soloDao.registerSolo(regSoloBean);
        Application application = new Application(bandId, soloId , "drums", "ciao, vieni a suonare!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.updateApplicationMessage(msg, code));
    }

    @Test
    @Order(3)
    void updateSoloAppliedTest(){
        RegisterBandBean regBandBean = new RegisterBandBean("PJ@", "asd", "PearlJam", "Seattle");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("luciano@", "dsa", "luciano", "verdi", "Milan");
        int soloId = soloDao.registerSolo(regSoloBean);
        RegisterSoloBean regSoloBean2 = new RegisterSoloBean("pancrazio@", "dsa", "pancrazio", "neri", "Rome");
        int soloId2 = soloDao.registerSolo(regSoloBean2);

        Application application = new Application(bandId, soloId , "drums, guitar", "hello world!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.addSoloToApplication(soloId2, code));

    }

    @Test
    @Order(4)
    void updateInstrumentTest(){

        RegisterBandBean regBandBean = new RegisterBandBean("MM@", "asd", "MuteMath", "New Orleans");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("quasimodo@", "dsa", "quasimodo", "pinco", "Milan");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, soloId , "drums, guitar", "hello world!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.updateApplicationInstrument("piano", code));


    }


    @Test
    @Order(5)
    void deleteApplicationTest() {
        RegisterBandBean regBandBean = new RegisterBandBean("pupo@", "asd", "pupi", "Roccacannuccia");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("qualcuno@", "dsa", "qualcuno", "pinco", "Milan");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application = new Application(bandId, soloId, "drums, guitar", "hello world!");
        int code = sut.createApplication(application).getApplicationCode();

        Assertions.assertTrue(sut.deleteApplicationByCode(code));
    }

    @Test
    @Order(6)
    void getApplicationsByBandIdTest(){
        RegisterBandBean regBandBean = new RegisterBandBean("gino@", "asd", "vannelli", "Toronto");
        int bandId = bandDao.registerBand(regBandBean);
        RegisterSoloBean regSoloBean = new RegisterSoloBean("tizio@", "dsa", "caio", "pinco", "Milan");
        int soloId = soloDao.registerSolo(regSoloBean);

        Application application1 = new Application(bandId, soloId, "guitar", "hello!");
        int code1 = sut.createApplication(application1).getApplicationCode();
        Application application2 = new Application(bandId, soloId, "drums", "world!");
        int code2 = sut.createApplication(application2).getApplicationCode();
        Application application3 = new Application(bandId, soloId, "drums, guitar", "hi!");
        int code3 = sut.createApplication(application3).getApplicationCode();

        //la stessa band ha inserito 3 applications, mi aspetto 3 se faccio la size della lista ritornata.
        Assertions.assertEquals(3, sut.getApplicationByBandId(bandId).size());
        List<Application> list =  sut.getApplicationByBandId(bandId);
        System.out.println(list.toString());

    }


    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
        sut.deleteApplications();
    }



}
