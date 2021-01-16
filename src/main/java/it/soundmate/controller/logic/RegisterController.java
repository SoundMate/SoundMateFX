/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;


import it.soundmate.bean.LoggedBean;
import it.soundmate.bean.LoginBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    private final UserDao userDao = new UserDao();

    public Solo registerSolo(RegisterSoloBean registerSoloBean) {
        try {
            SoloDao soloDao = new SoloDao(userDao);
            int id = soloDao.registerSolo(registerSoloBean);
            LoginBean loginBean = new LoginBean(registerSoloBean.getEmail(), registerSoloBean.getPassword());
            LoginController loginController = new LoginController(loginBean);
            return loginController.getFullSolo(id);
        } catch (InputException inputException) {
            log.error("Input Exception: {}", inputException.getMessage());
            return null;
        } catch (SQLException sqlException) {
            log.error("SQL Exception: {}", sqlException.getMessage());
            return null;
        }
    }

    public LoggedBean registerBand() {
        return null;
    }

    public RoomRenter registerRoomRenter(RegisterRenterBean registerRenterBean) {
        try {
            RoomRenterDao roomRenterDao = new RoomRenterDao(userDao);
            int id = roomRenterDao.registerRoomRenter(registerRenterBean);
            LoginBean loginBean = new LoginBean(registerRenterBean.getEmail(), registerRenterBean.getPassword());
            LoginController loginController = new LoginController(loginBean);
            return loginController.getFullRenter(id);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

}


