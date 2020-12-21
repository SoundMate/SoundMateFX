package it.soundmate.controller;

import it.soundmate.bean.LoginBean;
import it.soundmate.database.UserDao;
import it.soundmate.model.User;

public class LoginController {

    private final LoginBean loginBean;

    public LoginController(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean(){
        return this.loginBean;
    }

    public User login(LoginBean loginBean){
        if (checkFields(loginBean)){
            return UserDao.getInstance().getByEmailAndPassword(loginBean.getEmail(), loginBean.getPassword());
        } else {
            return null;
        }
    }

    private boolean checkFields(LoginBean loginBean) {
        return !loginBean.getEmail().isEmpty() && !loginBean.getPassword().isEmpty();
    }
}

