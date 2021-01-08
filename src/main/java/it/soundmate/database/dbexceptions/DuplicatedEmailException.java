package it.soundmate.database.dbexceptions;

public class DuplicatedEmailException extends DBException {
    public DuplicatedEmailException(String emailExistsErr) {
        super(emailExistsErr);
    }
}
