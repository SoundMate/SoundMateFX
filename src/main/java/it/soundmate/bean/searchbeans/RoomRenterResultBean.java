/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 13:40
 * Last edited: 12/01/21, 13:40
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.UserType;

public class RoomRenterResultBean extends UserResultBean {

    private final String roomRenterName;

    public RoomRenterResultBean(int id, String email, String encodedImg, String roomRenterName) {
        super(id, email, encodedImg, UserType.ROOM_RENTER);
        this.roomRenterName = roomRenterName;
    }

    public String getRoomRenterName() {
        return roomRenterName;
    }
}
