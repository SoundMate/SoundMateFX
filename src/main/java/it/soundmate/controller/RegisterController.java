package it.soundmate.controller;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.UserDao;
import it.soundmate.database.dbexceptions.DBException;
import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterController {


    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private RegisterBean registerBean;
    private final UserDao userDao = UserDao.getInstance();

    public RegisterController(RegisterBean registerBean) {
        this.registerBean = registerBean;
    }

    public RegisterController(){}

    public void setRegisterBean(RegisterBean registerBean) {
        this.registerBean = registerBean;
    }

    public User registerUser() {
        if (this.registerBean instanceof RegisterSoloBean) {
            logger.info("Registering Solo User");
            RegisterSoloBean registerSoloBean = (RegisterSoloBean) this.registerBean;
            try {
                int result = userDao.registerSolo(registerSoloBean);
                if (result > 0) {
                    return userDao.login(registerSoloBean.getEmail(), registerSoloBean.getPassword());
                } else {
                    logger.error("userDao.registerSolo returned {}", result);
                    return null;
                }
            } catch (DBException e) {
                logger.error("DATABASE EXCEPTION");
                return null;
            }
        } else if (this.registerBean instanceof RegisterBandBean) {
            logger.info("Band Registration");
            return null;
        } else {
            //this.registerBean instanceof RegisterRenterBean
            logger.info("Room Renter Registration");
            return null;
        }
    }

}
