/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:58
 */

package it.soundmate.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class User {


    private int userID;
    private String email;
    private String password;
    private String encodedImg;
    private ArrayList<File> photos;
    private UserType userType;

    public User(){}

    public User(int userID, String email, String password, String encodedImg, UserType userType) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.encodedImg = encodedImg;
    }

    public int getUserID() {
        return userID;
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

    public List<File> getPhotos() {
        return photos;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEncodedImg() {
        return encodedImg;
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }

}
