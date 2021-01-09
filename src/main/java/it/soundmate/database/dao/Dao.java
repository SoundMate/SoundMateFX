/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 20:19
 */

package it.soundmate.database.dao;


import it.soundmate.bean.registerbeans.RegisterBean;

public interface Dao<T> {
    int register(RegisterBean registerBean);
    int update(T t);
    int delete(T t);
    T get(int id);
}
