package it.soundmate.controller;

import it.soundmate.bean.LoggedBean;
import it.soundmate.bean.LoginBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class LoginController {

    private LoginBean loginBean;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserDao userDao = new UserDao();

    public LoginController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoggedBean login(){
        if (checkFields()) {
            return new LoggedBean();
        }else {
            return this.userDao.login(this.loginBean);
        }
    }

    public Solo getFullSolo(int id) throws SQLException {
        SoloDao soloDao = new SoloDao(Connector.getInstance(), userDao);
        return soloDao.getSoloByID(id);
    }

    public Band getFullBand(int id){
        BandDao bandDao = new BandDao(Connector.getInstance(), userDao);
        return bandDao.getBandByID(id);
    }

    public RoomRenter getFullRenter(int id){
        RoomRenterDao renterDao = new RoomRenterDao(Connector.getInstance(), userDao);
        return renterDao.getRenterByID(id);
    }

    private boolean checkFields() {
        return ("".equals(loginBean.getEmail()) || "".equals(loginBean.getPassword()));
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}

