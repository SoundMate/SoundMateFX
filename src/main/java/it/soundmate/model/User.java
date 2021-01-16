/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:58
 */

package it.soundmate.model;


import java.io.File;
import java.util.List;

public class User {

    private int id;
    private String email;
    private String password;
    private String city;
    private List<File> photos;
    private String encodedImg;

    public User(){}

    public User(int id, String email, String password, String city) {
        this.id = id;
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
        return city;
    }

    public void setCountry(String city) {
        this.city = city;
    }

    public UserType getUserType() {
        return null;
    }

    public List<File> getPhotos() {
        return photos;
    }

    public String getEncodedImg() {
        return encodedImg;
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }

}
