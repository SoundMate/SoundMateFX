/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 07/02/21, 12:01
 * Last edited: 07/02/21, 12:01
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.controller.logic.MessagesController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.*;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookingDetailView extends VBox {

    private final ProfileView profileView;
    private final Booking booking;
    private final RoomRenter roomRenter;
    private final RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();

    private final TextArea messageTextArea = new TextArea();
    private final Button sendMessageBtn = UIUtils.createStyledButton("Send message", new SendMessageAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public BookingDetailView(ProfileView profileView, Booking booking, RoomRenter roomRenter) {
        this.booking = booking;
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.setPadding(new Insets(25));
        this.setSpacing(10);
        Label title = new Label("Booking request for "+booking.getRoom().getName());
        title.setStyle(Style.HEADER_TEXT);
        Label date = new Label("Date: "+booking.getDate());
        Label time = new Label("From: "+booking.getStartTime()+"\nTo: "+booking.getEndTime());
        Label booker = new Label("Booker: "+booking.getBooker().getName());
        date.setStyle(Style.HIGH_LABEL);
        time.setStyle(Style.HIGH_LABEL);
        booker.setStyle(Style.HIGH_LABEL);
        this.getChildren().addAll(title, date, time, booker);
        HBox buttonsHBox = new HBox();
        Button acceptBtn = UIUtils.createStyledButton("Accept", new AcceptAction(booking));
        Button declineBtn = UIUtils.createStyledButton("Decline", new DeclineAction(booking));
        buttonsHBox.getChildren().add(acceptBtn);
        UIUtils.addRegion(null, buttonsHBox);
        buttonsHBox.getChildren().add(declineBtn);
        this.getChildren().add(buttonsHBox);
        Label messageLabel = new Label("Add a message");
        messageLabel.setStyle(Style.MID_LABEL);
        this.getChildren().addAll(messageLabel, messageTextArea);
        this.getChildren().add(sendMessageBtn);
        this.getChildren().add(backBtn);
    }


    private class SendMessageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                Message message = new Message(roomRenter.getId(), booking.getBookerUserId(), "Booking #"+booking.getCode(), messageTextArea.getText(), UserType.ROOM_RENTER);
                MessagesController messagesController = new MessagesController();
                messagesController.sendMessage(message);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Message has been sent");
                alert.showAndWait();
            } catch (RepositoryException repositoryException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Message has NOT been sent: "+repositoryException.getMessage());
                alert.showAndWait();
            }
        }
    }

    private class AcceptAction implements EventHandler<ActionEvent> {
        private final Booking booking;

        public AcceptAction(Booking booking) {
            this.booking = booking;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                roomRenterProfileGraphicController.acceptBooking(this.booking);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Booking has been accepted");
                alert.showAndWait();
            } catch (RepositoryException repositoryException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Booking has NOT been accepted: "+repositoryException.getMessage());
                alert.showAndWait();
            }
        }
    }

    private class DeclineAction implements EventHandler<ActionEvent> {
        private final Booking booking;

        public DeclineAction(Booking booking) {
            this.booking = booking;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                roomRenterProfileGraphicController.declineBooking(this.booking);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Booking has been declined and deleted");
                alert.showAndWait();
                profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
            } catch (RepositoryException repositoryException) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Booking has NOT been declined and deleted: "+repositoryException.getMessage());
                alert.showAndWait();
            }
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
        }
    }
}
