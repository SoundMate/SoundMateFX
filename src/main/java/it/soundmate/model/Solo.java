package it.soundmate.model;

import java.util.List;

import static it.soundmate.model.UserType.SOLO;


public class Solo extends User{

    private String firstName;
    private String lastName;
    private int age;
    private List<Band> bands;
    private List<String> favGenres;
    private List<String> instruments;
    private static final UserType userType= SOLO;

    public Solo(int id, String fName, String lName, int age,String email, String password) {
        super.setId(id);
        this.firstName = fName;
        this.lastName = lName;
        super.setEmail(email);
        super.setPassword(password);
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }

    public List<String> getFavGenres() {
        return favGenres;
    }

    public void setFavGenres(List<String> favGenres) {
        this.favGenres = favGenres;
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

}