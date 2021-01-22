/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 17:38
 * Last edited: 20/01/21, 17:38
 */

package it.soundmate.controller.graphic.register;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.controller.logic.RegisterController;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.User;

public class RegisterBandGraphicController implements RegisterGraphicController {

    private final RegisterController registerController = new RegisterController();

    @Override
    public User registerUser(RegisterBean registerBean) {
        try {
            return registerController.registerBand((RegisterBandBean) registerBean);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        } catch (DuplicatedEmailException duplicatedEmailException) {
            throw new DuplicatedEmailException(duplicatedEmailException.getMessage());
        }
    }
}
