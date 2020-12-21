/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:58
 */

package it.soundmate.model;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private ArrayList<File> photos;
    private InputStream profilePic;
    private UserType userType;
    //video

    public User(){}

    public User(int userID, String email, String firstName, String lastName, String password, InputStream profilePic, UserType userType) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.profilePic = profilePic;
        this.userType = userType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public InputStream getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(InputStream profilePic) {
        this.profilePic = profilePic;
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

    public void uploadPhoto(File photo) {
        this.photos.add(photo);
    }

    public void removePhoto(File photo) {
        for (File currentPhoto: this.photos) {
            if (currentPhoto.equals(photo)) {
                this.photos.remove(currentPhoto);
                break;
            }
        }
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
