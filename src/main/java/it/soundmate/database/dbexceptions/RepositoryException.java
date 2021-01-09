/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 19:59
 * Last edited: 07/01/21, 19:35
 */

package it.soundmate.database.dbexceptions;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException{
    public RepositoryException(String message) {
        super(message);
    }
    public RepositoryException(String message, SQLException cause) {
        super(message, cause);
    }
}