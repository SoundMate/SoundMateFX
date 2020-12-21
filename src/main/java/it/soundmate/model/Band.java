/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.ArrayList;
import java.util.List;

public class Band {

    private int bandID;
    private ArrayList<String> genres;
    private ArrayList<Solo> members;
    private ArrayList<String> socials;
    private String bandName;
    //private Song songPreviews;

    public Band(int bandID){
        this.bandID = bandID;
    }


    public Band(List<String> genres, List<String> socials, List<Solo> members) {
        this.genres = (ArrayList<String>) genres;
        this.socials = (ArrayList<String>) socials;
        this.members = (ArrayList<Solo>) members;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = (ArrayList<String>) genres;
    }

    public List<Solo> getMembers() {
        return members;
    }

    public void setMembers(List<Solo> members) {
        this.members = (ArrayList<Solo>) members;
    }

    public List<String> getSocials() {
        return socials;
    }

    public void setSocials(List<String> socials) {
        this.socials = (ArrayList<String>) socials;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }
}
