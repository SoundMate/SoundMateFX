/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/02/21, 23:24
 * Last edited: 12/02/21, 23:24
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lorenzo Pantano
 * */

class SendMessageTest {

    private User testSender;
    private User testReceiver;
    private final UserDao userDao = new UserDao();

    @BeforeEach
    void setUp() {
        RegisterSoloBean registerSender = new RegisterSoloBean("testsender@gmail.com", "test", "Sender", "Sender last name", "Roma");
        RegisterSoloBean registerReceiver = new RegisterSoloBean("testreceiver@gmail.com", "test", "Receiver", "Receiver last name", "Roma");
        RegisterController registerController = new RegisterController();
        testSender = registerController.registerSolo(registerSender);
        testReceiver = registerController.registerSolo(registerReceiver);
    }

    @AfterEach
    void tearDown() {
        userDao.delete(testSender);
        userDao.delete(testReceiver);
    }

    @Test
    void sendMessage() {
        Message message = new Message(testSender.getId(), testReceiver.getId(), "Test", "Test message body", testSender.getUserType());
        MessagesController messagesController = new MessagesController();
        messagesController.sendMessage(message);
        List<Message> list = messagesController.getMessagesForUser(testReceiver);
        Message sentMessage = list.get(0);
        assertEquals(testReceiver.getId(), sentMessage.getIdReceiver());
    }
}