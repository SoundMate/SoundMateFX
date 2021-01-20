package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.PowerUserDao;
import it.soundmate.database.dao.UserDao;
import org.junit.jupiter.api.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class PowerUserTest {

    static Connector connector = Connector.getInstance();
    static PowerUserDao powerUserDao = new PowerUserDao();
    static UserDao userDao = new UserDao();


    @Test
//    @Order(2)
    void banTest(){
        RegisterBandBean registerBandBean = new RegisterBandBean("DT@", "sasa", "DT", "Boston");
        BandDao bandDao = new BandDao(userDao);
        bandDao.registerBand(registerBandBean);
        Assertions.assertTrue(powerUserDao.banUser("DT@"));
    }


    @Test
   // @Order(1)
    void unbanTest(){
        Assertions.assertTrue(powerUserDao.unbanEmail("DT@"));
    }

    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
        powerUserDao.unbanAll();
    }

}
