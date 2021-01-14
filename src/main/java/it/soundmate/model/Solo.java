/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.List;

import static it.soundmate.model.UserType.SOLO;


public class  Solo extends User {

    private String firstName;
    private String lastName;
    private int age;
    private List<Band> bands;
    private List<Genre> favGenres;
    private List<String> instruments;
    private static final UserType userType= SOLO;

    public Solo() {
    }

    public Solo(int id, String fName, String lName, int age, String email, String password) {
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

    public List<Genre> getFavGenres() {
        return favGenres;
    }

    public void setFavGenres(List<Genre> favGenres) {
        this.favGenres = favGenres;
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = instruments;

    }
    public void addInstruments(String instrument){
        this.instruments.add(instrument);
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    public void addGenre(Genre genre) {
        this.favGenres.add(genre);
    }
}