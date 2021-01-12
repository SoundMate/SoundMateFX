/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:39
 * Last edited: 12/01/21, 13:39
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.UserType;

public class BandResultBean extends UserResultBean {

    private final String bandName;

    public BandResultBean(int id, String email, String encodedImg, String bandName) {
        super(id, email, encodedImg, UserType.BAND);
        this.bandName = bandName;
    }

    public String getBandName() {
        return bandName;
    }
}
