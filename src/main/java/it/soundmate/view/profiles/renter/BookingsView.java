/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 07/02/21, 11:41
 * Last edited: 07/02/21, 11:41
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.model.Booking;
import it.soundmate.model.RoomRenter;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class BookingsView extends VBox {

    private final ProfileView profileView;
    private final RoomRenter roomRenter;
    private final List<Booking> bookingList;
    private final BookingsListView bookingsListView;

    //UI
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());


    public BookingsView(ProfileView profileView, RoomRenter roomRenter, List<Booking> bookingList) {
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        this.bookingList = bookingList;
        this.bookingsListView = new BookingsListView(profileView, roomRenter);
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.setPadding(new Insets(25));
        this.setSpacing(10);
        Label label = new Label("Bookings");
        label.setStyle(Style.HEADER_TEXT);
        this.getChildren().add(label);
        ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList(bookingList);
        this.bookingsListView.setItems(bookingObservableList);
        this.bookingsListView.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        this.getChildren().add(bookingsListView);
        this.getChildren().add(backBtn);
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
        }
    }
}
