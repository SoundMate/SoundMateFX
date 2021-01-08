package it.soundmate.bean.registerbeans;

import it.soundmate.model.User;
import it.soundmate.model.UserType;

public class RegisterBandBean extends RegisterBean {

    private String bandName;

    public RegisterBandBean(){}

    public RegisterBandBean(String email, String password, String bandName) {
        super(email, password, UserType.BAND);
        this.bandName = bandName;
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
    public User registerUser() {
        return null;
    }
}
