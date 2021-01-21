//package it.soundmate.controller.database;
//
//import it.soundmate.bean.registerbeans.RegisterBandBean;
//import it.soundmate.bean.registerbeans.RegisterSoloBean;
//import it.soundmate.database.dao.SoloDao;
//import it.soundmate.database.dao.UserDao;
//import it.soundmate.model.AnagraphicData;
//import it.soundmate.model.Solo;
//import org.junit.jupiter.api.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class SoloDaoTest {
//
//    static UserDao userDao = new UserDao();
//    static SoloDao soloDao = new SoloDao(userDao);
//    static AnagraphicData registryData = new AnagraphicData("pippo", "pluto", 32, "Rome");
//    static Solo solo = new Solo(0, registryData, "pippo@", "asd");
//
//    @BeforeAll
//    static void setUp(){
//        RegisterSoloBean registerSoloBean = new RegisterSoloBean("pippo@", "asd", "pippo", "pluto");
//        solo.setId(soloDao.registerSolo(registerSoloBean));
//    }
//
//
//
//    @Test
//    @Order(1)
//    void insertInstrumentsTest(){
//        List<String> genres = new ArrayList<>();
//        genres.add("guitar");
//        genres.add("flute");
//
//
//        Assertions.assertTrue(soloDao.insertInstruments(solo, genres));
//    }
//
//    @Test
//    @Order(2)
//    void updateInstrumentTest(){
//        String sctrumenDo = "bass";
//        Assertions.assertTrue(soloDao.updateInstrument(solo, sctrumenDo));
//
//
//
//    }
//    @Test
//    @Order(3)
//    void getInstrumentsTest(){
//        Assertions.assertEquals(3, soloDao.getInstruments(solo).size());
//
//    }
//
//
//    @AfterAll
//    static void tearDown(){
//        userDao.deleteAll();
//    }
//
//
//
//}
