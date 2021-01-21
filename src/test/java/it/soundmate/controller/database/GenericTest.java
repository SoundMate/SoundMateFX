package it.soundmate.controller.database;

import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Room;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericTest {

    static UserDao userDao = new UserDao();
    static RoomRenterDao renterDao = new RoomRenterDao(userDao);



    @Test
    void codeReturnTest(){
        Room room = new Room("rosso", 120.0, "sasa", "img");
        Assertions.assertEquals(1, renterDao.insertRoom(room, 15));

    }

    @AfterAll
    static void tearDown(){
        renterDao.deleteAllRoom();
    }

}
