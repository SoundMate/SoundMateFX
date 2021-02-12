/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 11/01/21, 20:51
 * Last edited: 11/01/21, 20:51
 */

package it.soundmate.controller.graphic;

import it.soundmate.bean.LoginBean;
import it.soundmate.controller.logic.LoginController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.database.dbexceptions.UserNotFoundException;
import it.soundmate.model.User;

/**
 * Login Graphic Controller
 *
 * Controller grafico della Login View.
 * La classe LoginView crea l'oggetto LoginBean, che viene passato come
 * parametro al metodo login() di quest classe, che a sua volta comunicherà
 * con il controller applicativo chiamando il metodo login() di quest'ultimo.
 *
 * Contiene anche metodi per fare l'update della view.
 * */

public class LoginGraphicController {

    /**
     * Login
     *
     * @param loginBean LoginBean (String email, String password)
     * @return User returned user from LoginController.login(), throws exception if null
     * */
    public User login(LoginBean loginBean) {
        try {
            LoginController loginController = new LoginController(loginBean);
            return loginController.login();
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        } catch (UserNotFoundException userNotFoundException) {
            throw new UserNotFoundException(userNotFoundException.getMessage());
        }
    }
}
