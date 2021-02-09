/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.List;

import static it.soundmate.model.UserType.BAND;

public class Band extends User{

    private List<Solo> members;
    private String bandName;
    private List<Genre> genres;
    private static final UserType userType = BAND;

    public Band() {
    }

    public Band(String bandName) {
        this.bandName = bandName;
    }

    public Band(int id, String bandName, String password){
        super.setId(id);
        this.setBandName(bandName);
        super.setPassword(password);
    }

    public List<Solo> getMembers() {
        return members;
    }

    public void setMembers(List<Solo> members) {
        this.members = members;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    @Override
    public String getName() {
        return this.getBandName();
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    @Override
    public UserType getUserType() {
        return userType;
    }

    public void addGenre(Genre genre) {
        this.getGenres().add(genre);
    }
}
