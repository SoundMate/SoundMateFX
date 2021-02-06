/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 22:01
 * Last edited: 29/01/21, 22:01
 */

package it.soundmate.view.search;


import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.BookRoomController;
import it.soundmate.controller.logic.NotificationsController;
import it.soundmate.controller.logic.profiles.BandProfileController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.*;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NotificationsResults extends ListView<Notification> {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsResults.class);
    private final NotificationsController notificationsController = new NotificationsController();
    private final BookRoomController bookRoomController = new BookRoomController();
    private BandProfileController bandProfileController = new BandProfileController();

    public NotificationsResults() {
        this.setCellFactory(param -> new MessageResult());
        this.setPrefHeight(500);
        this.setPrefWidth(600);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class MessageResult extends ListCell<Notification> {
        @Override
        protected void updateItem(Notification notification, boolean empty) {
            super.updateItem(notification, empty);
            if (notification != null) {
                logger.info("Building message result");
                HBox hBox = buildResultHBox(notification);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(hBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private HBox buildResultHBox(Notification notification) {
            HBox messageHBox = new HBox();
            VBox messageVBox = new VBox();
            messageVBox.setPrefHeight(USE_COMPUTED_SIZE);
            messageHBox.setPadding(new Insets(10, 25, 0, 25));
            messageVBox.setPadding(new Insets(10));
            messageHBox.setSpacing(10);
            Label messageTypeLabel = new Label();
            messageVBox.getChildren().add(messageTypeLabel);
            Button markAsRead = UIUtils.createStyledButton("Mark As Read", new MarkAsReadAction(notification));
            if (notification.getMessageType() == MessageType.BOOK_ROOM_CONFIRMATION) {
                buildBookConfirmationMessage(notification, messageHBox, messageVBox, messageTypeLabel, markAsRead);
            } else if (notification.getMessageType() == MessageType.BOOK_ROOM_CANCELED){
                messageTypeLabel.setText("Booking Canceled");
                buildBookCanceledMessage(notification, messageHBox, messageVBox, messageTypeLabel, markAsRead);
            } else if (notification.getMessageType() == MessageType.JOIN_BAND_CONFIRMATION) {
                messageTypeLabel.setText("Join Request Accepted");
                buildJoinRequestAccepted(notification, messageHBox, messageTypeLabel, markAsRead);
            }
            return messageHBox;
        }

        private void buildJoinRequestAccepted(Notification notification, HBox messageHBox, Label messageTypeLabel, Button markAsRead) {
            logger.info("Building Join Request Accepted");
            seenMessageStyle(notification, messageHBox, messageTypeLabel, markAsRead);
            JoinRequestNotification joinRequestNotification = (JoinRequestNotification) notification;
            BandResultBean bandResultBean;
            if (joinRequestNotification.getJoinRequest().getBand() == null) {
                bandResultBean = bandProfileController.getBandNameByID(joinRequestNotification.getJoinRequest().getIdBand());
            } else bandResultBean = joinRequestNotification.getJoinRequest().getBand();
            messageTypeLabel.setText("Join Request Accepted from "+bandResultBean.getBandName());
            messageHBox.getChildren().add(messageTypeLabel);
            UIUtils.addRegion(null, messageHBox);
            messageHBox.getChildren().add(markAsRead);
        }

        private void buildBookCanceledMessage(Notification notification, HBox messageHBox, VBox messageVBox, Label messageTypeLabel, Button markAsRead) {
            logger.info("Building book canceled message");
            seenMessageStyle(notification, messageHBox, messageTypeLabel, markAsRead);
            BookingNotification bookingMessage = (BookingNotification) notification;
            messageTypeLabel.setText("Room Booking Canceled #" + bookingMessage.getBooking().getCode());
            Label messageLabel = new Label(MessageType.BOOK_ROOM_CANCELED.getMessageDesc()+" for "+((BookingNotification) notification).getBooking().getDate()+" from "+((BookingNotification) notification).getBooking().getStartTime() + " to "+((BookingNotification) notification).getBooking().getEndTime());
            messageLabel.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().add(messageLabel);
            messageHBox.getChildren().add(messageVBox);
            UIUtils.addRegion(null, messageHBox);
            messageHBox.getChildren().addAll(markAsRead);
        }

        private void buildBookConfirmationMessage(Notification notification, HBox messageHBox, VBox messageVBox, Label messageTypeLabel, Button markAsRead) {
            seenMessageStyle(notification, messageHBox, messageTypeLabel, markAsRead);
            BookingNotification bookingMessage = (BookingNotification) notification;
            messageTypeLabel.setText("Room Booking #" + bookingMessage.getBooking().getCode());
            Label messageLabel = new Label(MessageType.BOOK_ROOM_CONFIRMATION.getMessageDesc()+" for "+((BookingNotification) notification).getBooking().getDate()+" from "+((BookingNotification) notification).getBooking().getStartTime() + " to "+((BookingNotification) notification).getBooking().getEndTime());
            messageLabel.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().add(messageLabel);
            messageHBox.getChildren().add(messageVBox);
            UIUtils.addRegion(null, messageHBox);
            Button cancelBtn = UIUtils.createStyledButton("Cancel", new CancelBookingAction(bookingMessage.getBooking()));
            cancelBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
            messageHBox.getChildren().addAll(markAsRead, cancelBtn);
        }

        private void seenMessageStyle(Notification notification, HBox messageHBox, Label messageTypeLabel, Button markAsRead) {
            if (!notification.isSeen()) {
                Circle circle = new Circle();
                circle.setRadius(5);
                circle.setFill(Color.RED);
                messageHBox.getChildren().add(circle);
                messageTypeLabel.setStyle(Style.HIGH_LABEL);
            } else {
                messageTypeLabel.setStyle(Style.MID_LABEL);
                markAsRead.setVisible(false);
            }
        }
    }

    private class CancelBookingAction implements EventHandler<ActionEvent> {

        private final Booking booking;

        public CancelBookingAction(Booking booking) {
            this.booking = booking;
        }

        @Override
        public void handle(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Canceling booking "+booking.getCode()+" of "+booking.getDate());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                BookingNotification bookingMessageForUser = new BookingNotification(booking.getBookerUserId(), booking.getBookerUserId(), MessageType.BOOK_ROOM_CANCELED, false, booking);
                BookingNotification bookingMessageForRenter = new BookingNotification(booking.getBookerUserId(), booking.getRoom().getRenterID(), MessageType.BOOK_ROOM_CANCELED, false, booking);
                bookRoomController.cancelBooking(bookingMessageForUser);
                bookRoomController.cancelBooking(bookingMessageForRenter);
            }
        }
    }

    private class MarkAsReadAction implements EventHandler<ActionEvent> {

        private final Notification notification;

        public MarkAsReadAction(Notification notification) {
            this.notification = notification;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                notificationsController.markAsRead(notification);
            } catch (RepositoryException repositoryException) {
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            }
        }
    }
}
