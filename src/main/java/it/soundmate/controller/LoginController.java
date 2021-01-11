package it.soundmate.controller;

import it.soundmate.bean.LoggedBean;
import it.soundmate.bean.LoginBean;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {

    private LoginBean loginBean;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserDao userDao;
    private final SoloDao soloDao;
    private final BandDao bandDao;
    private final RoomRenterDao roomRenterDao;

    public LoginController(LoginBean loginBean, UserDao userDao, SoloDao soloDao, BandDao bandDao, RoomRenterDao roomRenterDao) {
        this.loginBean = loginBean;
        this.userDao = userDao;
        this.soloDao = soloDao;
        this.bandDao = bandDao;
        this.roomRenterDao = roomRenterDao;
    }

    public LoggedBean login(LoginBean loginBean){
        if (!checkFields()) {
            return new LoggedBean();
        }else
            return userDao.login(loginBean);
    }




    public Solo getFullSolo(int id){
            return soloDao.getSoloByID(id);
    }

    public Band getFullBand(int id){
        return bandDao.getBandByID(id);
    }

    public RoomRenter getFullRenter(int id){
        return roomRenterDao.getRenterByID(id);
    }




    private boolean checkFields() {
        return ("".equals(loginBean.getEmail()) && "".equals(loginBean.getPassword()));
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}

