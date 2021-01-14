package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.UserDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegBandTest {

    static UserDao userDao = new UserDao();
    static BandDao bandDao = new BandDao(userDao);


    @Test
    void testBandReg() {
        RegisterBandBean registerBandBean = new RegisterBandBean("DreamTheater@qualcosa.com", "DTSoundMate", "Dream Theater");
        Assertions.assertNotEquals(0, bandDao.regiBandController(registerBandBean), "SI E' ROTTO");
    }


    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
    }



}
