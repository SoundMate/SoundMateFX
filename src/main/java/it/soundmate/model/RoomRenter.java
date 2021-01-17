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
    private static final UserType userType = ROOM_RENTER;
    private AnagraphicData registryData = new AnagraphicData();

    public RoomRenter(){
        super();
    }

    public RoomRenter(int id, String email, String password, String address, AnagraphicData registryData) {
        super.setId(id);
        super.setEmail(email);
        super.setPassword(password);
        this.address = address;
        this.registryData = registryData;
    }

    public String getCity() {
        return this.registryData.getCity();
    }

    public void setCity(String city) {
        this.registryData.setCity(city);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return this.registryData.getFirstName();
    }

    public void setFirstName(String firstName) {
        this.registryData.setFirstName(firstName);
    }

    public String getLastName() {
        return this.registryData.getLastName();
    }

    public void setLastName(String lastName) {
        this.registryData.setLastName(lastName);
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
