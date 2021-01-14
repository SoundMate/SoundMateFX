package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.PowerUserDao;
import it.soundmate.database.dao.UserDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PowerUserTest {

    static Connector connector = Connector.getInstance();
    static PowerUserDao powerUserDao = new PowerUserDao();
    static UserDao userDao = new UserDao();


    @Test
    void banTest(){
        RegisterBandBean registerBandBean = new RegisterBandBean("DT@", "sasa", "DT");
        BandDao bandDao = new BandDao(userDao);
        bandDao.regiBandController(registerBandBean);
        Assertions.assertTrue(powerUserDao.banUser("DT@"));
    }


    @Test
    void unbanTest(){
        Assertions.assertTrue(powerUserDao.unbanEmail("DT@"));
    }

    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
        powerUserDao.unbanAll();
    }

}
