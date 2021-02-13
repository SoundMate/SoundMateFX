/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 21:32
 * Last edited: 29/01/21, 21:32
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.BookingDao;
import it.soundmate.database.dao.NotificationDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookRoomController {

    private final RoomRenterDao roomRenterDao = new RoomRenterDao();
    private final NotificationDao notificationDao = new NotificationDao();
    private final BookingDao bookingDao = new BookingDao();

    public void bookRoom(Booking booking) {
        try {
            bookingDao.sendBookingRequest(booking);
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
            notificationDao.sendBookingMessageToRenter(message);
            notificationDao.sendBookingMessageToUser(message);
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

    public List<Booking> getBookingsForRenter(RoomRenter roomRenter) {
        try {
            return bookingDao.getAllBookingsById(roomRenter);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public void declineBooking(Booking booking) {
        try {
            bookingDao.deleteBooking(booking);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public void acceptBooking(Booking booking) {
        try {
            bookingDao.acceptBooking(booking);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
