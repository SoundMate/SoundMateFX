package it.soundmate.bean;

public class LoginBean {

    private final String email;
    private final String password;


    public LoginBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
