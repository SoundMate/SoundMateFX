package it.soundmate.bean.registerbeans;


import it.soundmate.exceptions.InputException;
import it.soundmate.model.UserType;

import static it.soundmate.model.UserType.SOLO;

public class RegisterSoloBean extends RegisterBean {

    private String firstName;
    private String lastName;
    private static final UserType USER_TYPE = SOLO;

    public RegisterSoloBean() {
    }

    public RegisterSoloBean(String email, String password, String firstName, String lastName, String city) {
        super(email, password, city);
        this.firstName = firstName;
        this.lastName = lastName;
        if (!this.checkFields()) throw new InputException("Some fields are empty");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean checkFields(){
        return super.checkFields() && !this.firstName.isEmpty() && !this.lastName.isEmpty();
    }

    @Override
    public UserType getUserType() {
        return USER_TYPE;
    }
}
