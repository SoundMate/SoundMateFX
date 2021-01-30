/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:38
 */

package it.soundmate.model;


public class Room {

    private int renterID;
    private int code;
    private Double price;
    private String description;
    private String name;
    private String encodedImg;
    private boolean roomIsFree; //true means the room is free to be booked...

    public Room(String name, Double price, String description, String encodedImg){
        this.name = name;
        this.price = price;
        this.description = description;
        this.encodedImg = encodedImg;
    }

    public Room(int code, String name, Double price, String description, String encodedImg) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.encodedImg = encodedImg;
        this.description = description;
    }

    public String getEncodedImg() {
        return encodedImg;
    }

    public void setEncodedImg(String encodedImg) {
        this.encodedImg = encodedImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public boolean isRoomIsFree() {
        return roomIsFree;
    }

    public void setRoomIsFree(boolean roomIsFree) {
        this.roomIsFree = roomIsFree;
    }

    public int getRenterID() {
        return renterID;
    }

    public void setRenterID(int renterID) {
        this.renterID = renterID;
    }
}
