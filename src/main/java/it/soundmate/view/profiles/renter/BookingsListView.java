/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 07/02/21, 11:48
 * Last edited: 07/02/21, 11:48
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.model.Booking;
import it.soundmate.model.RoomRenter;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingsListView extends ListView<Booking> {

    private static final Logger logger = LoggerFactory.getLogger(BookingsListView.class);
    private final ProfileView profileView;
    private final RoomRenter roomRenter;

    public BookingsListView(ProfileView profileView, RoomRenter roomRenter) {
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        this.setCellFactory(param -> new BookingResult());
        this.setOrientation(Orientation.VERTICAL);
        this.setPrefHeight(200);
        this.setPrefWidth(525);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class BookingResult extends ListCell<Booking> {
        @Override
        protected void updateItem(Booking booking, boolean empty) {
            super.updateItem(booking, empty);
            if (booking != null) {
                HBox hBox = buildResultHBox(booking);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(hBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private HBox buildResultHBox(Booking booking) {
            logger.info("Building result booking ID {}, Room ID: {}", booking.getCode(), booking.getRoom().getCode());
            HBox bookingHBox = new HBox();
            bookingHBox.setAlignment(Pos.CENTER);
            bookingHBox.setSpacing(10);
            Label bookingRequestLabel = new Label("New booking request for "+booking.getRoom().getName());
            bookingRequestLabel.setStyle(Style.HIGH_LABEL);
            Button viewBtn = UIUtils.createStyledButton("View", new ViewBookingAction(booking));
            bookingHBox.getChildren().add(bookingRequestLabel);
            UIUtils.addRegion(null, bookingHBox);
            bookingHBox.getChildren().add(viewBtn);
            return bookingHBox;
        }

        private class ViewBookingAction implements EventHandler<ActionEvent> {

            private final Booking booking;

            public ViewBookingAction(Booking booking) {
                this.booking = booking;
            }

            @Override
            public void handle(ActionEvent event) {
                profileView.setProfilePage(new BookingDetailView(profileView, booking, roomRenter));
            }
        }
    }

}
