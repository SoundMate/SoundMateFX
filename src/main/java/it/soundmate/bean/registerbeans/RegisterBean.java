package it.soundmate.bean.registerbeans;


import it.soundmate.model.User;
import it.soundmate.model.UserType;

public abstract class RegisterBean {

    private String email;
    private String password;
    private UserType userType;

    public RegisterBean(){}

    public RegisterBean(String email, String password, UserType userType){
        this.email = email;
        this.password = password;
        this.userType = userType;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * checkFields()
     * @return true if fields are not empty, false otherwise
     */
    public boolean checkFields() {
        return !this.email.isEmpty() && !this.password.isEmpty();
    }

    public abstract User registerUser();

}
