/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 12/01/21, 13:30
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.LoggedBean;
import it.soundmate.bean.LoginBean;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.database.dbexceptions.UserNotFoundException;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class LoginController {

    private LoginBean loginBean;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserDao userDao = new UserDao();

    public LoginController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public User login() {
        if (checkFields()) {
            return null;
        } else {
            LoggedBean loggedBean;
            try {
                loggedBean = this.userDao.login(this.loginBean);
                switch (loggedBean.getUserType()) {
                    case SOLO:
                        return this.getFullSolo(loggedBean.getUserID());
                    case BAND:
                        return this.getFullBand(loggedBean.getUserID());
                    case ROOM_RENTER:
                        return this.getFullRenter(loggedBean.getUserID());
                    default:
                        logger.error("LoginController: usertype error");
                        throw new UserNotFoundException("User type doesn't exist");
                }
            } catch (LoginException e) {
                logger.error("Login Exception: {}", e.getMessage());
                return null;
            } catch (NullPointerException e) {
                logger.error("Null Pointer Exception: {}", e.getMessage());
                return null;
            }

        }
    }

    public Solo getFullSolo(int id) {
        SoloDao soloDao = new SoloDao(userDao);
        try {
            Solo soloUser = soloDao.getSoloByID(id);
            soloUser.setFavGenres(soloDao.getGenres(id));
            soloUser.setInstruments(soloDao.getInstruments(id));
            soloUser.setMessages(userDao.getNotificationsForUser(id));
            return soloUser;
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public Band getFullBand(int id){
        BandDao bandDao = new BandDao(userDao);
        Band band = bandDao.getBandByID(id);
        band.setGenres(bandDao.getGenres(id));
        band.setSocialLinks(bandDao.getSocialLinks(id));
        band.setMessages(userDao.getNotificationsForUser(id));
        return band;
    }

    public RoomRenter getFullRenter(int id){
        RoomRenterDao renterDao = new RoomRenterDao(userDao);
        RoomRenter roomRenter = renterDao.getRenterByID(id);
        roomRenter.setMessages(userDao.getNotificationsForUser(id));
        return roomRenter;
    }

    private boolean checkFields() {
        return ("".equals(loginBean.getEmail()) || "".equals(loginBean.getPassword()));
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}

