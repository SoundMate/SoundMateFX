package it.soundmate.bean.registerbeans;

import it.soundmate.exceptions.InputException;
import it.soundmate.model.UserType;

import static it.soundmate.model.UserType.ROOM_RENTER;

public class RegisterRenterBean extends RegisterBean {

    private String name;
    private String address;

    private static final UserType USER_TYPE = ROOM_RENTER;

    public RegisterRenterBean() {
    }

    public RegisterRenterBean(String email, String password, String address, String name, String city) {
        super(email, password, city);
        this.address = address;
        this.name = name;
        if (!this.checkFields()) {
            throw new InputException("Some fields are empty");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean checkFields(){
        return super.checkFields() && !"".equals(this.name) && !"".equals(this.address);
    }

    @Override
    public UserType getUserType() {
        return USER_TYPE;
    }
}
