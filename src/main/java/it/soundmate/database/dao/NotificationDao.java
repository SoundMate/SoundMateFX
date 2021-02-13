/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/02/21, 13:56
 * Last edited: 13/02/21, 13:56
 */

package it.soundmate.database.dao;

import it.soundmate.database.Connector;
import it.soundmate.database.NotificationFactory;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(NotificationDao.class);


    public List<Notification> getNotificationsForUser(int id) {
        log.info("Getting messages for user {}", id);
        List<Notification> notificationList = new ArrayList<>();
        String query = "select * from notifications left join booking on notifications.booking_id = booking.code left join join_request jr on jr.code = notifications.join_request where receiver = (?)";
        try (Connection conn = connector.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                log.info("Result set");
                String type = resultSet.getString("type");
                MessageType messageType = MessageType.returnMessageType(type);
                NotificationFactory notificationFactory = new NotificationFactory(messageType);
                Notification notification = notificationFactory.getNotification(resultSet);
                notificationList.add(notification);
            }
            return notificationList;
        } catch (SQLException e) {
            throw new InputException("Unable to read messages: "+e.getMessage());
        } catch (InputException inputException) {
            throw new InputException(inputException.getMessage());
        }
    }

    public void sendBookingMessageToUser(BookingNotification bookingMessage) {
        String sql = "INSERT INTO notifications (sender, receiver, type, seen, booking_id) VALUES (?, ?, ?, ?, ?) RETURNING message_id";
        try (Connection conn = connector.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookingMessage.getSender());
            preparedStatement.setInt(2, bookingMessage.getSender());
            executeNotificationQuery(bookingMessage, preparedStatement);
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    public void sendBookingMessageToRenter(BookingNotification message) {
        String sql = "INSERT INTO notifications (sender, receiver, type, seen, booking_id) VALUES (?, ?, ?, ?, ?) RETURNING message_id";
        try (Connection conn = connector.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, message.getSender());
            preparedStatement.setInt(2, message.getReceiver());
            ResultSet resultSet = executeNotificationQuery(message, preparedStatement);
            if (resultSet.next()) {
                message.setMessageId(resultSet.getInt("message_id"));
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    public ResultSet executeNotificationQuery(BookingNotification message, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(3, MessageType.BOOK_ROOM_CONFIRMATION.name());
        preparedStatement.setBoolean(4, false);
        preparedStatement.setInt(5, message.getBooking().getCode());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            message.setMessageId(resultSet.getInt("message_id"));
        }
        return resultSet;
    }
}
