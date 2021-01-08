package it.soundmate.database.dbexceptions;

public class BannedAccountException extends DBException {
    public BannedAccountException(String accBannedErr) {
        super(accBannedErr);
    }
}
