/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;

public class Room {

    private int code;
    private Double price;
    private String description;
    private String roomAddress;
    private boolean roomIsFree; //true means the room is free to be booked...

    public Room(int code, Double price, String description, String roomAddress) {
        this.code = code;
        this.price = price;
        this.description = description;
        this.roomAddress = roomAddress;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomAddress() {
        return roomAddress;
    }

    public void setRoomAddress(String roomAddress) {
        this.roomAddress = roomAddress;
    }

    public boolean isRoomIsFree() {
        return roomIsFree;
    }

    public void setRoomIsFree(boolean roomIsFree) {
        this.roomIsFree = roomIsFree;
    }
}
