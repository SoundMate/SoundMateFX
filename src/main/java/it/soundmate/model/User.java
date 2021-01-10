package it.soundmate.model;


import java.io.File;
import java.io.InputStream;
import java.util.List;

public abstract class User {
    private int id;
    private String email;
    private String password;
    private String country;
    private List<File> photos;
    private InputStream profilePic;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public abstract UserType getUserType();

    //TODO
    //photos methods...or whatever
}