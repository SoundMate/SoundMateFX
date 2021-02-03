/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 02/02/21, 23:00
 * Last edited: 02/02/21, 23:00
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.MessageDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import java.util.List;

public class MessagesController {

    MessageDao messageDao = new MessageDao();

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

}
