/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/02/21, 14:18
 * Last edited: 13/02/21, 14:18
 */

package it.soundmate.database;

import it.soundmate.database.dao.BookingDao;
import it.soundmate.database.dao.JoinRequestDao;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationFactory {

    private final MessageType messageType;
    private final BookingDao bookingDao = new BookingDao();
    private final JoinRequestDao joinRequestDao = new JoinRequestDao();
    public static final String BOOKING_ID = "booking_id";

    public NotificationFactory(MessageType messageType) {
        this.messageType = messageType;
    }

    public Notification getNotification(ResultSet resultSet) throws SQLException {
        int sender = resultSet.getInt("sender");
        int receiver = resultSet.getInt("receiver");
        boolean seen = resultSet.getBoolean("seen");
        int messageID = resultSet.getInt("message_id");
        switch (this.messageType) {
            case BOOK_ROOM_CONFIRMATION:
                int bookingID = resultSet.getInt(BOOKING_ID);
                Booking booking = bookingDao.getBookingByID(bookingID);
                BookingNotification bookingMessage = new BookingNotification(sender,receiver, MessageType.BOOK_ROOM_CONFIRMATION, seen, booking);
                bookingMessage.setMessageId(messageID);
                return bookingMessage;
            case BOOK_ROOM_CANCELED:
                int bookingCanceledID = resultSet.getInt(BOOKING_ID);
                Booking bookingCanceled = bookingDao.getBookingByID(bookingCanceledID);
                BookingNotification bookingNotificationCanceled = new BookingNotification(sender,receiver, MessageType.BOOK_ROOM_CANCELED, seen, bookingCanceled);
                bookingNotificationCanceled.setMessageId(messageID);
                return bookingNotificationCanceled;
            case JOIN_BAND_CONFIRMATION:
                int requestID = resultSet.getInt("join_request");
                JoinRequest joinRequest = joinRequestDao.getJoinRequestByID(requestID);
                JoinRequestNotification joinRequestNotification = new JoinRequestNotification(sender, receiver, MessageType.JOIN_BAND_CONFIRMATION, seen);
                joinRequestNotification.setMessageId(messageID);
                joinRequestNotification.setJoinRequest(joinRequest);
                return joinRequestNotification;
            case JOIN_BAND_CANCELED:
                int requestCanceledID = resultSet.getInt("join_request");
                JoinRequest joinRequestCanceled = joinRequestDao.getJoinRequestByID(requestCanceledID);
                JoinRequestNotification joinRequestNotificationCanceled = new JoinRequestNotification(sender, receiver, MessageType.JOIN_BAND_CANCELED, seen);
                joinRequestNotificationCanceled.setMessageId(messageID);
                joinRequestNotificationCanceled.setJoinRequest(joinRequestCanceled);
                return joinRequestNotificationCanceled;
            default:
                throw new InputException("Unable to determine type of notification");
        }
    }
}
