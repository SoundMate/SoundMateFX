package it.soundmate.bean.registerbeans;

import it.soundmate.controller.RegisterController;
import it.soundmate.model.User;
import it.soundmate.model.UserType;

public class RegisterSoloBean extends RegisterBean {

    private String firstName;
    private String lastName;

    public RegisterSoloBean(){}

    public RegisterSoloBean(String email, String password, String firstName, String lastName) {
        super(email, password, UserType.SOLO);
        this.firstName = firstName;
        this.lastName = lastName;
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
    public User registerUser() {
        RegisterController soloRegisterController = new RegisterController(this);
        return soloRegisterController.registerUser();
    }

}
