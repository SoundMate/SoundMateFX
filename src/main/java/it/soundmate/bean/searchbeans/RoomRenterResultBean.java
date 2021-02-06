/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 22/01/21, 15:58
 * Last edited: 22/01/21, 15:58
 */

package it.soundmate.bean.searchbeans;

import it.soundmate.model.Room;
import it.soundmate.model.UserType;

import java.util.List;

public class RoomRenterResultBean extends UserResultBean {

    private String name;
    private String address;
    private List<Room> rooms;

    public RoomRenterResultBean() {
    }

    public RoomRenterResultBean(int id, String email, String encodedImg, String name, String city) {
        super(id, email, encodedImg, city, UserType.ROOM_RENTER);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
