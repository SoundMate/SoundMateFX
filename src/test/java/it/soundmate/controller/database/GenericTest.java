package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Room;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenericTest {

    static UserDao userDao = new UserDao();
    static RoomRenterDao renterDao = new RoomRenterDao();

    @Test
    void codeReturnTest(){
        RegisterRenterBean registerRenterBean = new RegisterRenterBean("pippo@", "prova", "viadelculo", "gino", "roma");
        int id = renterDao.registerRoomRenter(registerRenterBean);
        Room room = new Room("rosso", 120.0, "sasa", "img");
        Assertions.assertEquals(1, renterDao.insertRoom(room, id));
    }

    @AfterAll
    static void tearDown(){
        renterDao.deleteAllRoom();
        userDao.deleteAll();
    }


}
