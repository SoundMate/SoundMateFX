/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

import java.util.ArrayList;

public class RoomRenter extends User {

    private ArrayList<Room> rooms;
    private String address;


    public RoomRenter(User user) {
        super(user.getUserID(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getProfilePic(), UserType.BAND_ROOM_MANAGER);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }
    public void removeRoom(Room room){
        for (int i = 0; i < this.rooms.size(); i++) {
            if (room.equals(this.rooms.get(i))) {
                this.rooms.remove(i);
                break;
            }
        }
    }


}
