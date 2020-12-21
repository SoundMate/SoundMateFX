/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 11/12/20, 15:42
 */

package it.soundmate.database;

public interface Dao<T> {
    T getByID(long id);
    T getByEmailAndPassword(String email, String password);
}
