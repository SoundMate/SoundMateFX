package it.soundmate.bean.registerbeans;

import it.soundmate.model.User;
import it.soundmate.model.UserType;

public class RegisterRenterBean extends RegisterBean {

    private String address;
    private String name;


    public RegisterRenterBean(){}

    public RegisterRenterBean(String email, String password, String address, String name) {
        super(email, password, UserType.ROOM_RENTER);
        this.address = address;
        this.name = name;
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
        return super.checkFields() && !this.address.isEmpty() && !this.name.isEmpty();
    }

    @Override
    public User registerUser() {
        return null;
    }
}
