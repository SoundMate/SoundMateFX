package it.soundmate.bean.registerbeans;


import it.soundmate.model.UserType;

public abstract class RegisterBean {

    private String email;
    private String password;
    private String city;

    protected RegisterBean() {
    }

    protected RegisterBean(String email, String password, String city){
        this.email = email;
        this.password = password;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public abstract UserType getUserType();
    /**
     * checkFields()
     * @return true if fields are not empty, false otherwise
     */

    public boolean checkFields() {
        return !"".equals(this.email) && !"".equals(this.password) && this.email.contains("@") && !"".equals(this.city);
    }

}
