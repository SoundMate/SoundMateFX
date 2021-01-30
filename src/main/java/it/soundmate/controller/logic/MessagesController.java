/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 22:26
 * Last edited: 29/01/21, 22:26
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Message;
import it.soundmate.model.User;

import java.util.List;

public class MessagesController {

    private final UserDao userDao = new UserDao();

    public List<Message> getMessagesForUser(User user) {
        try {
            return userDao.getMessagesForUser(user.getId());
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void markAsRead(Message message) {
        try {
            userDao.markMessageAsRead(message);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
