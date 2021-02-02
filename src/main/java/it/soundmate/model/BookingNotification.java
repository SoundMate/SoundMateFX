/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 21:41
 * Last edited: 29/01/21, 21:41
 */

package it.soundmate.model;

public class BookingNotification extends Notification {

    private Booking booking;

    public BookingNotification(int sender, int receiver, MessageType messageType, boolean seen, Booking booking) {
        super(sender, receiver, messageType, seen);
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
