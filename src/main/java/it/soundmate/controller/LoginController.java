package it.soundmate.controller;

import it.soundmate.bean.LoginBean;
import it.soundmate.database.UserDao;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
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
            User loggedUser = UserDao.getInstance().getByEmailAndPassword(loginBean.getEmail(), loginBean.getPassword());
            if (loggedUser.getProfilePic() != null && !Cache.getInstance().checkProfilePicInCache(loggedUser)) {
                if (Cache.getInstance().saveProfilePicToCache(loggedUser)){
                    logger.info("Saved Profile Pic to Cache");
                } else {
                    logger.info("Profile Pic Not Saved");
                }
            }
            return loggedUser;
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

