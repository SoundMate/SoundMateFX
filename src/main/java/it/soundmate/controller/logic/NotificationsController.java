/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 22:26
 * Last edited: 29/01/21, 22:26
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.NotificationDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Notification;
import it.soundmate.model.User;

import java.util.List;

public class NotificationsController {

    private final NotificationDao notificationDao = new NotificationDao();
    private final UserDao userDao = new UserDao();

    public List<Notification> getMessagesForUser(User user) {
        try {
            return notificationDao.getNotificationsForUser(user.getId());
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void markAsRead(Notification notification) {
        try {
            userDao.markMessageAsRead(notification);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
