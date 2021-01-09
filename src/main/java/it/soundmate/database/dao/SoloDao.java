/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 21:10
 */

package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoloDao extends UserDao {

    /*Singleton*/

    private static SoloDao instance = null;

    public static SoloDao getInstance() {
        if (instance == null) {
            instance = new SoloDao();
        }
        return instance;
    }


    @Override
    public int register(RegisterBean registerBean) {
        return 0;
    }


    @Override
    public Solo get(int id) {
        return null;
    }
}
