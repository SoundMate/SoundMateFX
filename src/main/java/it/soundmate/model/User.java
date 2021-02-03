/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:58
 */

package it.soundmate.model;


import java.io.File;
import java.util.List;

public abstract class User {

    private int id;
    private String email;
    private String password;
    private List<File> photos;
    private String encodedImg;
    private AnagraphicData anagraphicData = new AnagraphicData();
    private List<Notification> notifications;

    protected User(){}

    protected User(int id, String email, String password, AnagraphicData anagraphicData) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.anagraphicData = anagraphicData;
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

    public List<File> getPhotos() {
        return photos;
    }

    public String getEncodedImg() {
        return encodedImg;
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }

    public abstract UserType getUserType();

    public void setCity(String city) {
        this.anagraphicData.setCity(city);
    }

    public String getCity() {
        return this.anagraphicData.getCity();
    }

    public void setFirstName(String firstName){
        this.anagraphicData.setFirstName(firstName);
    }

    public String getFirstName(){
        return this.anagraphicData.getFirstName();
    }

    public void setLastName(String lastName){
        this.anagraphicData.setLastName(lastName);
    }

    public String getLastName(){
        return this.anagraphicData.getLastName();
    }

    public void setAge(int age){
        this.anagraphicData.setAge(age);
    }

    public int getAge(){
        return this.anagraphicData.getAge();
    }

    public List<Notification> getMessages() {
        return notifications;
    }

    public void setMessages(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public abstract String getName();
}
