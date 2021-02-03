package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Band;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RegBandTest {

    static UserDao userDao = new UserDao();
    static BandDao bandDao = new BandDao(userDao);


    @Test
    void testBandReg() {
        RegisterBandBean registerBandBean = new RegisterBandBean("DreamTheater@qualcosa.com", "DTSoundMate", "Dream Theater", "Chicago");
        Assertions.assertNotEquals(0, bandDao.registerBand(registerBandBean), "SI E' ROTTO");
    }


    @AfterAll
    static void tearDown(){
        userDao.deleteAll();
    }

}
