/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 28/01/21, 21:09
 * Last edited: 28/01/21, 21:09
 */

package it.soundmate.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private LocalTime endTime;
    private int roomID;
    private User bookingUser;
    private LocalDate date;
    private LocalTime startTime;

    public Booking(int roomID, User bookingUser, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.roomID = roomID;
        this.bookingUser = bookingUser;
        this.date = date;
        this.startTime = startTime;this.endTime = endTime;}

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public void setBookingUser(User bookingUser) {
        this.bookingUser = bookingUser;
    }

    public int getRoomID() {
        return roomID;
    }

    public User getBookingUser() {
        return bookingUser;
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
}
