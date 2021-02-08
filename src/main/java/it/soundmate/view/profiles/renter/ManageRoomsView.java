/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 21/01/21, 16:21
 * Last edited: 21/01/21, 16:21
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.model.Room;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;

public class ManageRoomsView extends VBox {

    private final ProfileView profileView;
    private final RoomRenter roomRenter;
    private final ListView<Room> roomsListView = new ListView<>();

    //UI
    private final Button addRoomBtn = UIUtils.createStyledButton("Add Room", new AddRoomAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public ManageRoomsView(ProfileView profileView, RoomRenter roomRenter) {
        this.profileView = profileView;
        this.roomRenter = roomRenter;
        buildManageRoomsVBox(roomRenter);
    }

    private void buildManageRoomsVBox(RoomRenter roomRenter) {
        this.setPadding(new Insets(25));
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setSpacing(20);
        this.setPrefHeight(500);

        //Name and add button
        HBox nameAndAddBtn = new HBox();
        nameAndAddBtn.setPrefHeight(USE_COMPUTED_SIZE);
        Label nameLabel = new Label(roomRenter.getName() + " Rooms");
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameAndAddBtn.getChildren().add(nameLabel);
        UIUtils.addRegion(null, nameAndAddBtn);
        nameAndAddBtn.getChildren().add(addRoomBtn);
        this.getChildren().add(nameAndAddBtn);

        //ListView of rooms
        if (roomRenter.getRooms() == null || roomRenter.getRooms().isEmpty()) {
            Label emptyLabel = new Label("No rooms added");
            emptyLabel.setStyle(Style.LOW_LABEL);
            this.getChildren().add(emptyLabel);
        } else {
            this.roomsListView.setStyle("-fx-border-color: transparent;");
            this.roomsListView.setCellFactory(param -> new RoomHBoxManage());
            List<Room> roomList = roomRenter.getRooms();
            ObservableList<Room> roomObservableList = FXCollections.observableArrayList(roomList);
            this.roomsListView.setItems(roomObservableList);
            this.getChildren().add(roomsListView);
        }

        this.getChildren().add(backBtn);
    }


    private class AddRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new AddRoomView(profileView, roomRenter));
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
        }
    }
}
