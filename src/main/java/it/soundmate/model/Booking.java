/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 28/01/21, 21:09
 * Last edited: 28/01/21, 21:09
 */

package it.soundmate.model;

import it.soundmate.bean.messagebeans.UserMessageBean;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {

    private int code;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Room room;
    private int bookerUserId;
    private boolean isAccepted = false;
    private int idRenter;
    private UserMessageBean booker;


    public Booking() {
    }

    public Booking(Room room, int bookerUserId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.room = room;
        this.bookerUserId = bookerUserId;
        this.date = date;
        this.startTime = startTime;this.endTime = endTime;
    }


    public Booking withCode(int code){
        Booking booking = new Booking();
        booking.setRoom(this.room);
        booking.setStartTime(this.startTime);
        booking.setEndTime(this.endTime);
        booking.setDate(this.date);
        booking.setBookerUserId(this.bookerUserId);
        booking.setCode(code);
        return booking;
    }


    public int getIdRenter() {
        return idRenter;
    }

    public void setIdRenter(int idRenter) {
        this.idRenter = idRenter;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getBookerUserId() {
        return bookerUserId;
    }

    public void setBookerUserId(int bookerUserId) {
        this.bookerUserId = bookerUserId;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public UserMessageBean getBooker() {
        return booker;
    }

    public void setBooker(UserMessageBean booker) {
        this.booker = booker;
    }

}
