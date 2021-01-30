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
    private LocalTime endTime;
    private Room room;
    private int bookingUser;
    private LocalDate date;
    private LocalTime startTime;

    public Booking(Room room, int bookingUser, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.room = room;
        this.bookingUser = bookingUser;
        this.date = date;
        this.startTime = startTime;this.endTime = endTime;}

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(int bookingUser) {
        this.bookingUser = bookingUser;
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
