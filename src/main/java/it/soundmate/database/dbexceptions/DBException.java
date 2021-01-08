package it.soundmate.database.dbexceptions;

public class DBException extends RuntimeException {
    public DBException(String message) {
        super(message);
    }
}
