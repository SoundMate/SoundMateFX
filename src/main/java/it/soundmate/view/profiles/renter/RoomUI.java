/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 21/01/21, 22:15
 * Last edited: 21/01/21, 22:00
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.model.Room;
import it.soundmate.utils.Cache;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class RoomUI extends VBox {

    public RoomUI(Room room, int renterID) {
        buildRoomUI(room, renterID);
    }

    private void buildRoomUI(Room room, int renterID) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.setPrefHeight(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);

        //Image
        if (room.getEncodedImg() != null) {
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(75);
            rectangle.setWidth(65);
            rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getRoomPicFromCache(renterID, room.getCode()))));
            this.getChildren().add(rectangle);
        } else {
            Label noImageLabel = new Label("No room image");
            noImageLabel.setStyle(Style.LOW_LABEL);
            this.getChildren().add(noImageLabel);
        }

        Label nameLabel = new Label(room.getName());
        nameLabel.setStyle(Style.MID_LABEL);
        this.getChildren().add(nameLabel);
    }

}
