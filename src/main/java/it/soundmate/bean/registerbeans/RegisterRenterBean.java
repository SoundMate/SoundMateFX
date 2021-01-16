package it.soundmate.bean.registerbeans;

import it.soundmate.exceptions.InputException;
import it.soundmate.model.UserType;

import static it.soundmate.model.UserType.ROOM_RENTER;

public class RegisterRenterBean extends RegisterBean {

    private String firstName;
    private String lastName;
    private String name;
    private String city;
    private String address;

    private static final UserType USER_TYPE = ROOM_RENTER;



    public RegisterRenterBean(String email, String password, String firstName, String lastName, String address, String name, String city) {
        super(email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.name = name;
        this.city = city;
        if (!this.checkFields()) {
            throw new InputException("Some fields are empty");
        }
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

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean checkFields(){
        return super.checkFields() && !"".equals(this.name) &&
                !"".equals(this.firstName) && !"".equals(this.lastName) && !"".equals(this.city)
                && !"".equals(this.address);
    }

    @Override
    public UserType getUserType() {
        return USER_TYPE;
    }
}
