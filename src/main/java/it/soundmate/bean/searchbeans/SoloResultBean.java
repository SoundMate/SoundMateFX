/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:19
 * Last edited: 12/01/21, 13:19
 */

package it.soundmate.bean.searchbeans;


import it.soundmate.model.UserType;

import java.util.ArrayList;
import java.util.List;

public class SoloResultBean extends UserResultBean {

    private String firstName;
    private String lastName;
    private final List<String> genreList = new ArrayList<>();
    private final List<String> instrumentList = new ArrayList<>();

    public SoloResultBean() {
    }

    public SoloResultBean(int id, String email, String encodedProfileImg, String firstName, String lastName, String city) {
        super(id, email, encodedProfileImg, city, UserType.SOLO);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setGenres(List<String> genreList) {
        this.genreList.clear();
        this.genreList.addAll(genreList);
    }

    public List<String> getGenreList() {
        return genreList;
    }

    public List<String> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<String> instrumentList) {
        this.instrumentList.clear();
        this.instrumentList.addAll(instrumentList);
    }

    @Override
    public String getName() {
        return this.getFirstName()+" "+this.getLastName();
    }
}
