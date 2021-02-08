/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 21/01/21, 22:15
 * Last edited: 21/01/21, 22:15
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.model.Room;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class RoomHBoxManage extends ListCell<Room> {

    @Override
    protected void updateItem(Room room, boolean b) {
        super.updateItem(room, b);
        if (room != null) {
            HBox hBox = buildRoomHBox(room);
            this.setStyle("-fx-background-color: #232323;");
            setGraphic(hBox);
        } else {
            this.setStyle("-fx-background-color: #232323");
        }
    }

    private HBox buildRoomHBox(Room room) {
        HBox roomHBox = new HBox();
        roomHBox.setPrefHeight(USE_COMPUTED_SIZE);
        roomHBox.setSpacing(10);
        Label roomName = new Label(room.getName());
        roomName.setStyle(Style.HIGH_LABEL);
        roomHBox.getChildren().add(roomName);
        UIUtils.addRegion(null, roomHBox);
        Button editBtn = UIUtils.createStyledButton("Edit", new EditRoomAction(room));
        roomHBox.getChildren().add(editBtn);
        return roomHBox;
    }

    private class EditRoomAction implements EventHandler<ActionEvent> {

        final Room room;

        public EditRoomAction(Room room) {
            this.room = room;
        }

        @Override
        public void handle(ActionEvent event) {
            //metodo vuoto, sonar smell
        }
    }
}
