/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;


import it.soundmate.bean.LoginBean;
import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
        } catch (RepositoryException sqlException) {
            log.error("Repository Exception: {}", sqlException.getMessage());
            return null;
        }
    }

    public Band registerBand(RegisterBandBean registerBandBean) {
        try {
            BandDao bandDao = new BandDao(userDao);
            int id = bandDao.register(registerBandBean);
            LoginBean loginBean = new LoginBean(registerBandBean.getEmail(), registerBandBean.getPassword());
            LoginController loginController = new LoginController(loginBean);
            return loginController.getFullBand(id);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        } catch (DuplicatedEmailException d) {
            throw new DuplicatedEmailException(d.getMessage());
        }
    }

    public RoomRenter registerRoomRenter(RegisterRenterBean registerRenterBean) {
        try {
            RoomRenterDao roomRenterDao = new RoomRenterDao();
            int id = roomRenterDao.registerRoomRenter(registerRenterBean);
            LoginBean loginBean = new LoginBean(registerRenterBean.getEmail(), registerRenterBean.getPassword());
            LoginController loginController = new LoginController(loginBean);
            return loginController.getFullRenter(id);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

}


