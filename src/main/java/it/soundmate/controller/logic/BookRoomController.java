/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 21:32
 * Last edited: 29/01/21, 21:32
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookRoomController {

    private final UserDao userDao = new UserDao();
    private final RoomRenterDao roomRenterDao = new RoomRenterDao(userDao);

    public void bookRoom(Booking booking) {
        try {
            roomRenterDao.bookRoom(booking);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public void checkAvailability(LocalDate date, LocalTime start, LocalTime end, Room room) {
        try {
            roomRenterDao.checkRoomAvailability(date, start, end, room);
        } catch (InputException | RepositoryException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void sendBookingInfo(Booking booking, int renterID) {
        try {
            BookingNotification message = new BookingNotification(booking.getBookerUserId(), renterID, MessageType.BOOK_ROOM_CONFIRMATION, false, booking);
            roomRenterDao.sendBookingMessageToRenter(message);
            userDao.sendBookingMessageToUser(message);
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void cancelBooking(BookingNotification bookingMessage) {
        try {
            roomRenterDao.cancelBooking(bookingMessage);
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }
}
