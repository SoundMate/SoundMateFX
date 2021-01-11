package it.soundmate.bean.registerbeans;


import it.soundmate.model.User;
import it.soundmate.model.UserType;

public abstract class RegisterBean {

    private String email;
    private String password;


    public RegisterBean(String email, String password){
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract UserType getUserType();

    /**
     * checkFields()
     * @return true if fields are not empty, false otherwise
     */

    //??????
    public boolean checkFields() {
        return !this.email.isEmpty() && !this.password.isEmpty();
    }

}
