/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.ArrayList;
import java.util.List;

public class Solo extends User {

    private ArrayList<String> favouriteGenres = new ArrayList<>();
    private int age;
    private ArrayList<String> instrument = new ArrayList<>();
    private ArrayList<Band> bands = new ArrayList<>();


    public Solo(User user) {
        super(user.getUserID(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getProfilePic(), UserType.SOLO);
    }

    public Solo(User user, List<String> favouriteGenres, int age, List<String> instrument, List<Band> bands) {
        this(user);
        this.favouriteGenres.addAll(favouriteGenres);
        this.age = age;
        this.instrument.addAll(instrument);
        this.bands.addAll(bands);
    }

    public void joinBand(Band band){
        this.bands.add(band);
    }

    public void leaveBand(Band band) {
        for (int i = 0; i < this.bands.size(); i++) {
            if (band.equals(this.bands.get(i))) {
                this.bands.remove(i);
                break;
            }
        }
    }

    public void setBands(List<Band> bands) {
        this.bands.addAll(bands);
    }

    public List<String> getFavouriteGenres() {
        return favouriteGenres;
    }

    public void setFavouriteGenres(List<String> favouriteGenres) {
        this.favouriteGenres.addAll(favouriteGenres);
    }

    public void addFavGenre(String favGenre) {
        this.favouriteGenres.add(favGenre);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getInstrument() {
        return instrument;
    }

    public void setInstrument(List<String> instrument) {
        this.instrument = (ArrayList<String>) instrument;
    }

}
