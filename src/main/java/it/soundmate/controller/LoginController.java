package it.soundmate.controller;

import it.soundmate.bean.LoginBean;
import it.soundmate.database.UserDao;
import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginController {

    private LoginBean loginBean;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(){}

    public LoginController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public User login(){
        if (checkFields()){
            logger.info("Check Fields Ok");
           return UserDao.getInstance().login(loginBean.getEmail(), loginBean.getPassword());
        } else {
            return null;
        }
    }

    private boolean checkFields() {
        return !loginBean.getEmail().isEmpty() && !loginBean.getPassword().isEmpty();
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}

