package it.soundmate.bean.registerbeans;

import it.soundmate.model.UserType;

import static it.soundmate.model.UserType.ROOM_RENTER;

public class RegisterRenterBean extends RegisterBean {

    private String firstName;
    private String lastName;
    private String address;

    private static final UserType USER_TYPE = ROOM_RENTER;



    public RegisterRenterBean(String email, String password, String firstName, String lastName, String address) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public boolean checkFields(){
        return super.checkFields() && !this.address.isEmpty() && !this.firstName.isEmpty() && !this.lastName.isEmpty();
    }

    @Override
    public UserType getUserType() {
        return USER_TYPE;
    }
}
