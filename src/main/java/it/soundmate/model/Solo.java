/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.ArrayList;
import java.util.List;

import static it.soundmate.model.UserType.SOLO;


public class  Solo extends User {

    private AnagraphicData registryData = new AnagraphicData();
    private List<Band> bands;
    private List<Genre> favGenres;
    private List<String> instruments;
    private static final UserType userType= SOLO;

    public Solo() {
    }

    public Solo(int id, AnagraphicData registryData, String email, String password) {
        super.setId(id);
        super.setEmail(email);
        super.setPassword(password);
        this.registryData = registryData;
    }

    public void setFirstName(String firstName){
        this.registryData.setFirstName(firstName);
    }

    public String getFirstName(){
        return this.registryData.getFirstName();
    }

    public void setLastName(String lastName){
        this.registryData.setLastName(lastName);
    }

    public String getLastName(){
        return this.registryData.getLastName();
    }

    public void setAge(int age){
        this.registryData.setAge(age);
    }

    public int getAge(){
        return this.registryData.getAge();
    }

    public void setCity(String city){
        this.registryData.setCity(city);
    }

    public String getCity(){
        return this.registryData.getCity();
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
        this.favGenres = new ArrayList<>(favGenres);
    }

    public void addGenre(Genre genre) {
        this.favGenres.add(genre);
    }

    public List<String> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<String> instruments) {
        this.instruments = new ArrayList<>(instruments);

    }
    public void addInstruments(String instrument){
        this.instruments.add(instrument);
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

}