/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 16/01/21, 20:43
 * Last edited: 16/01/21, 20:43
 */

package it.soundmate.controller.graphic.register;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.controller.logic.RegisterController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.User;

public class RegisterRenterGraphicController implements RegisterGraphicController {

    private final RegisterController registerController = new RegisterController();

    @Override
    public User registerUser(RegisterBean registerBean) {
       try {
           return registerController.registerRoomRenter((RegisterRenterBean) registerBean);
       } catch (RepositoryException repositoryException) {
           throw new RepositoryException(repositoryException.getMessage());
       }
    }
}
