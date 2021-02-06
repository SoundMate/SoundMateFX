package it.soundmate.bean.registerbeans;

import it.soundmate.exceptions.InputException;
import it.soundmate.model.UserType;

import static it.soundmate.model.UserType.BAND;

public class RegisterBandBean extends RegisterBean {

    private String bandName;
    private static final UserType USER_TYPE = BAND;

    public RegisterBandBean() {
    }

    public RegisterBandBean(String email, String password, String bandName, String city) {
        super(email, password, city);
        this.bandName = bandName;
        if (!this.checkFields()) {
            throw new InputException("Some fields are empty");
        }
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }


    @Override
    public boolean checkFields(){
        return super.checkFields() && !this.bandName.isEmpty();
    }

    @Override
    public UserType getUserType() {
        return USER_TYPE;
    }
}
