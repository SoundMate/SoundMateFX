package it.soundmate.bean;


public class RegisterBean {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String bandOrRoomName;

    public RegisterBean(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public RegisterBean(String firstName, String lastName, String email, String password, String bandOrRoomName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.bandOrRoomName = bandOrRoomName;
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

    public String getBandOrRoomName() {
        return bandOrRoomName;
    }

    public void setBandOrRoomName(String bandOrRoomName) {
        this.bandOrRoomName = bandOrRoomName;
    }
}
