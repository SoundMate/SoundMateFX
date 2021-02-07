/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:00
 * Last edited: 02/02/21, 23:00
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.messagebeans.UserMessageBean;
import it.soundmate.database.dao.JoinRequestDao;
import it.soundmate.database.dao.MessageDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.*;

import java.util.List;

public class MessagesController {

    private final MessageDao messageDao = new MessageDao();
    private final JoinRequestDao joinRequestDao = new JoinRequestDao();

    public List<Message> getMessagesForUser(User user) {
        try {
            return messageDao.getMessagesByUserId(user);
        } catch (RepositoryException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public Message sendMessage(Message message) {
        try {
            if (message.getSubject().equals("")) throw new InputException("Subject cannot be null");
            return messageDao.insertMessage(message);
        } catch (RepositoryException repositoryException){
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public UserMessageBean getSender(int idSender) {
        try {
            UserDao userDao = new UserDao();
            return userDao.getSenderInfo(idSender);
        } catch (RepositoryException | InputException e) {
            throw new InputException(e.getMessage());
        }
    }

    public void applyForBand(Application application, JoinRequest joinRequest) {
        try {
            joinRequestDao.sendRequestToApplication(application, joinRequest);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
