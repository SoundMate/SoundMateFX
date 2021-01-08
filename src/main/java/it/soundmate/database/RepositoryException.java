package it.soundmate.database;

import java.sql.SQLException;

public class RepositoryException extends RuntimeException{
    public RepositoryException(String message) {
        super(message);
    }
    public RepositoryException(String message, SQLException cause) {
        super(message, cause);
    }
}