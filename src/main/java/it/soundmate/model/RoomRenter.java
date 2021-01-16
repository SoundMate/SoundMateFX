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
    private String name;
    private String city;
    private String address;
    private static final UserType userType = ROOM_RENTER;

    public RoomRenter(){
        super();
    }

    public RoomRenter(User user, String firstName, String lastName, String name, String address) {
        super(user.getId(), user.getEmail(), user.getPassword(), user.getCountry());
        this.address = address;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
