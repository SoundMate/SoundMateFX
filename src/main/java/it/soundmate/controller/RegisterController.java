package it.soundmate.controller;

import it.soundmate.bean.LoginBean;
import it.soundmate.bean.RegisterBean;
import it.soundmate.database.UserDao;
import it.soundmate.model.User;
import it.soundmate.model.UserType;

public class RegisterController {

    private final RegisterBean registerBean;
    private final UserDao userDao = UserDao.getInstance();

    public RegisterController(RegisterBean registerBean) {
        this.registerBean = registerBean;
    }

    public boolean checkFields(UserType userType){
        if (userType == UserType.SOLO) {
            return !this.registerBean.getEmail().isEmpty() && !this.registerBean.getFirstName().isEmpty()
                    && !this.registerBean.getPassword().isEmpty() && !this.registerBean.getLastName().isEmpty();
        } else {
            return !this.registerBean.getEmail().isEmpty() && !this.registerBean.getFirstName().isEmpty()
                    && !this.registerBean.getPassword().isEmpty() && !this.registerBean.getLastName().isEmpty()
                    && !this.registerBean.getBandOrRoomName().isEmpty();
        }
    }

    public User registerUser(UserType userType) {
        int type;
        switch (userType){
            case SOLO:
               type = 1;
               break;
            case BAND_MANAGER:
                type = 2;
                break;
            case BAND_ROOM_MANAGER:
                type = 3;
                break;
            default:
                type = 0;
        }
        //If registering is ok --> Login and return the new user
        if (userDao.registerUser(this.registerBean.getEmail(), this.registerBean.getPassword(), this.registerBean.getFirstName(), this.registerBean.getLastName(), type)){
            LoginBean loginBean = new LoginBean(this.registerBean.getEmail(), this.registerBean.getPassword());
            LoginController loginController = new LoginController(loginBean);
            User registeredUser = loginController.login();
            userDao.registerSoloFromUsers(registeredUser.getUserID());
            return registeredUser;
        }
        else return null;
    }
}
