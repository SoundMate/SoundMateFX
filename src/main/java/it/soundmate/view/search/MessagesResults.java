/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 22:01
 * Last edited: 29/01/21, 22:01
 */

package it.soundmate.view.search;


import it.soundmate.constants.Style;
import it.soundmate.controller.logic.BookRoomController;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Booking;
import it.soundmate.model.BookingMessage;
import it.soundmate.model.Message;
import it.soundmate.model.MessageType;
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

public class MessagesResults extends ListView<Message> {

    private static final Logger logger = LoggerFactory.getLogger(MessagesResults.class);
    private final MessagesController messagesController = new MessagesController();
    private final BookRoomController bookRoomController = new BookRoomController();

    public MessagesResults() {
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

    public class MessageResult extends ListCell<Message> {
        @Override
        protected void updateItem(Message message, boolean empty) {
            super.updateItem(message, empty);
            if (message != null) {
                logger.info("Building message result");
                HBox hBox = buildResultHBox(message);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(hBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private HBox buildResultHBox(Message message) {
            HBox messageHBox = new HBox();
            VBox messageVBox = new VBox();
            messageVBox.setPrefHeight(USE_COMPUTED_SIZE);
            messageHBox.setPadding(new Insets(10, 25, 0, 25));
            messageVBox.setPadding(new Insets(10));
            messageHBox.setSpacing(10);
            Label messageTypeLabel = new Label();
            messageVBox.getChildren().add(messageTypeLabel);
            Button markAsRead = UIUtils.createStyledButton("Mark As Read", new MarkAsReadAction(message));
            if (message.getMessageType() == MessageType.BOOK_ROOM_CONFIRMATION) {
                buildBookConfirmationMessage(message, messageHBox, messageVBox, messageTypeLabel, markAsRead);
            } else if (message.getMessageType() == MessageType.BOOK_ROOM_CANCELED){
                messageTypeLabel.setText("Booking Canceled");
                buildBookCanceledMessage(message, messageHBox, messageVBox, messageTypeLabel, markAsRead);
            }
            return messageHBox;
        }

        private void buildBookCanceledMessage(Message message, HBox messageHBox, VBox messageVBox, Label messageTypeLabel, Button markAsRead) {
            logger.info("Building book canceled message");
            seenMessageStyle(message, messageHBox, messageTypeLabel, markAsRead);
            BookingMessage bookingMessage = (BookingMessage) message;
            messageTypeLabel.setText("Room Booking Canceled #" + bookingMessage.getBooking().getBookingID());
            Label messageLabel = new Label(MessageType.BOOK_ROOM_CANCELED.getMessageDesc()+" for "+((BookingMessage) message).getBooking().getDate()+" from "+((BookingMessage) message).getBooking().getStartTime() + " to "+((BookingMessage) message).getBooking().getEndTime());
            messageLabel.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().add(messageLabel);
            messageHBox.getChildren().add(messageVBox);
            UIUtils.addRegion(null, messageHBox);
            messageHBox.getChildren().addAll(markAsRead);
        }

        private void buildBookConfirmationMessage(Message message, HBox messageHBox, VBox messageVBox, Label messageTypeLabel, Button markAsRead) {
            seenMessageStyle(message, messageHBox, messageTypeLabel, markAsRead);
            BookingMessage bookingMessage = (BookingMessage) message;
            messageTypeLabel.setText("Room Booking #" + bookingMessage.getBooking().getBookingID());
            Label messageLabel = new Label(MessageType.BOOK_ROOM_CONFIRMATION.getMessageDesc()+" for "+((BookingMessage) message).getBooking().getDate()+" from "+((BookingMessage) message).getBooking().getStartTime() + " to "+((BookingMessage) message).getBooking().getEndTime());
            messageLabel.setStyle(Style.LOW_LABEL);
            messageVBox.getChildren().add(messageLabel);
            messageHBox.getChildren().add(messageVBox);
            UIUtils.addRegion(null, messageHBox);
            Button cancelBtn = UIUtils.createStyledButton("Cancel", new CancelBookingAction(bookingMessage.getBooking()));
            cancelBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
            messageHBox.getChildren().addAll(markAsRead, cancelBtn);
        }

        private void seenMessageStyle(Message message, HBox messageHBox, Label messageTypeLabel, Button markAsRead) {
            if (!message.isSeen()) {
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
            alert.setContentText("Canceling booking "+booking.getBookingID()+" of "+booking.getDate());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                BookingMessage bookingMessageForUser = new BookingMessage(booking.getBookingUser(), booking.getBookingUser(), MessageType.BOOK_ROOM_CANCELED, false, booking);
                BookingMessage bookingMessageForRenter = new BookingMessage(booking.getBookingUser(), booking.getRoom().getRenterID(), MessageType.BOOK_ROOM_CANCELED, false, booking);
                bookRoomController.cancelBooking(bookingMessageForUser);
                bookRoomController.cancelBooking(bookingMessageForRenter);
            }
        }
    }

    private class MarkAsReadAction implements EventHandler<ActionEvent> {

        private final Message message;

        public MarkAsReadAction(Message message) {
            this.message = message;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                messagesController.markAsRead(message);
            } catch (RepositoryException repositoryException) {
                logger.error("Repository Exception: {}", repositoryException.getMessage());
            }
        }
    }
}
