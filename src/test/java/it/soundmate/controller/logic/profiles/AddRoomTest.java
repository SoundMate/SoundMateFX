/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/02/21, 23:18
 * Last edited: 12/02/21, 23:18
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.bean.AddRoomBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.controller.logic.RegisterController;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Room;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lorenzo Pantano
 * */

class AddRoomTest {

    private final UserDao userDao = new UserDao();
    private User testUser;

    @BeforeEach
    void setUp() {
        RegisterRenterBean registerRenterBean = new RegisterRenterBean("testrenter@gmail.com", "test", "Test Address", "Test Room Renter", "Roma");
        RegisterController registerController = new RegisterController();
        testUser = registerController.registerRoomRenter(registerRenterBean);
        System.out.println(testUser.getId() + testUser.getEmail());
    }

    @AfterEach
    void tearDown() {
        userDao.delete(testUser);
    }

    @Test
    void addRoom() {
        AddRoomBean addRoomBean = new AddRoomBean("Test Room", "50", "Test Description", "");
        RoomRenterProfileController roomRenterProfileController = new RoomRenterProfileController();
        int roomID = roomRenterProfileController.addRoom(addRoomBean, (RoomRenter) testUser);
        Room room = userDao.getRoomByID(roomID);
        assertEquals(testUser.getId(), room.getRenterID());
    }
}