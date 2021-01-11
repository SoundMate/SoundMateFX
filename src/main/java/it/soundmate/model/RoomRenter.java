/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;


import java.util.List;

import static it.soundmate.model.UserType.ROOM_RENTER;


public class RoomRenter extends User {

    private List<Room> rooms;
    private String firstName;
    private String lastName;
    private static final UserType userType = ROOM_RENTER;


    public RoomRenter() {
    }

    public RoomRenter(int id, String fName, String lName, String password){
        super.setId(id);
        this.setFirstName(fName);
        this.setLastName(lName);
        super.setPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

}
