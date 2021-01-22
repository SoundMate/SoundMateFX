/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:39
 * Last edited: 12/01/21, 13:39
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.UserType;

import java.util.List;

public class BandResultBean extends UserResultBean {

    private final String bandName;
    private List<String> genres;

    public BandResultBean(int id, String email, String encodedImg, String bandName, String city) {
        super(id, email, encodedImg, city, UserType.BAND);
        this.bandName = bandName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }
}
