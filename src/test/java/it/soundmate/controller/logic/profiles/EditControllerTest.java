/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/02/21, 22:41
 * Last edited: 12/02/21, 22:41
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.controller.logic.RegisterController;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Lorenzo Pantano
 * */

class EditControllerTest {

    private EditController editController;
    private User testUser;
    private final UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        RegisterSoloBean registerSoloBean = new RegisterSoloBean("l.pantano00@gmail.com", "lorenzo", "Lorenzo", "Pantano", "Roma");
        RegisterController registerController = new RegisterController();
        testUser = registerController.registerSolo(registerSoloBean);
        editController = new EditController();
        System.out.println(testUser.getId() + testUser.getEmail());
    }

    @AfterEach
    void tearDown() {
        userDao.delete(testUser);
    }

    @Test
    void updateEmail() {
        editController.updateEmail("l.pantano01@gmail.com", testUser);
        String email = testUser.getEmail();
        assertEquals("l.pantano01@gmail.com", email);
    }

    @Test
    void updatePassword() {
        editController.updatePassword("testPassword", testUser);
        String password = testUser.getPassword();
        assertEquals("testPassword", password);
    }

    @Test
    void updateCity() {
        editController.updateCity("Madrid", testUser);
        String city = testUser.getCity();
        assertEquals("Madrid", city);
    }
}