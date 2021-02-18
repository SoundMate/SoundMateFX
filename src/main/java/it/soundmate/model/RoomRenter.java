/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;


import java.util.ArrayList;
import java.util.List;

import static it.soundmate.model.UserType.ROOM_RENTER;


public class RoomRenter extends User {

    private List<Room> rooms;
    private String address;
    private String name;
    private static final UserType userType = ROOM_RENTER;

    public RoomRenter(){
        super();
    }

    public RoomRenter(int id, String email, String password, String address, String name) {
        super.setId(id);
        super.setEmail(email);
        super.setPassword(password);
        this.address = address;
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
        this.rooms = new ArrayList<>(rooms);
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

}
