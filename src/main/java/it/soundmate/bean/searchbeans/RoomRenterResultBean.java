/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 22/01/21, 15:58
 * Last edited: 22/01/21, 15:58
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.UserType;

public class RoomRenterResultBean extends UserResultBean {

    private String name;

    public RoomRenterResultBean(int id, String email, String encodedImg, String name, String city) {
        super(id, email, encodedImg, city, UserType.ROOM_RENTER);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
