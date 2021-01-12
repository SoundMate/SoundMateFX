/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 11/01/21, 20:51
 * Last edited: 11/01/21, 20:51
 */

package it.soundmate.database.dbexceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
