/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 28/01/21, 21:09
 * Last edited: 28/01/21, 21:09
 */

package it.soundmate.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private int bookingID;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Room room;
    private int bookerUser;



    public Booking(Room room, int bookerUser, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.room = room;
        this.bookerUser = bookerUser;
        this.date = date;
        this.startTime = startTime;this.endTime = endTime;}

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getBookerUser() {
        return bookerUser;
    }

    public void setBookerUser(int bookerUser) {
        this.bookerUser = bookerUser;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getBookingID() {
        return bookingID;
    }
}
