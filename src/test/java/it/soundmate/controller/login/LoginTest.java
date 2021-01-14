package it.soundmate.controller.login;

import it.soundmate.bean.LoginBean;
import it.soundmate.database.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LoginTest {





    @Test
    void loginTest(){
        LoginBean loginBean = new LoginBean("pippo@", "cacca");
        UserDao userDao = new UserDao();
        Assertions.assertTrue(userDao.login(loginBean).isQueryResult());
    }



}
