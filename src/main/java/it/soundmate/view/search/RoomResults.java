/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 22:46
 * Last edited: 25/01/21, 22:46
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.search.SearchResultsGraphicController;
import it.soundmate.model.Room;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchView;
import it.soundmate.view.main.SearchingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomResults extends ListView<Room> {

    private static final Logger logger = LoggerFactory.getLogger(RoomResults.class);
    private final SearchResultsGraphicController searchResultsGraphicController = new SearchResultsGraphicController();
    private final int renterID;
    private final SearchingView searchingView;
    private final RoomRenterResultBean roomRenterResultBean;

    public RoomResults(int renterID, SearchingView searchingView, RoomRenterResultBean roomRenterResultBean) {
        this.renterID = renterID;
        this.searchingView = searchingView;
        this.roomRenterResultBean = roomRenterResultBean;
        this.setCellFactory(param -> new RoomResult());
        this.setOrientation(Orientation.HORIZONTAL);
        this.setPrefHeight(200);
        this.setPrefWidth(525);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class RoomResult extends ListCell<Room> {
        @Override
        protected void updateItem(Room room, boolean empty) {
            super.updateItem(room, empty);
            if (room != null) {
                VBox vBox = buildResultVBox(room);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(vBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private VBox buildResultVBox(Room room) {
            VBox resultVBox = ResultsView.buildResultVBox();
            Rectangle profilePic = new Rectangle();
            profilePic.setWidth(75);
            profilePic.setHeight(50);
            if (room.getEncodedImg() != null) {
                ImagePattern imagePattern = new ImagePattern(new Image(Cache.getInstance().getRoomPicFromCache(renterID, room.getCode())));
                profilePic.setFill(imagePattern);
            }
            Label name = new Label(room.getName());
            name.setStyle(Style.MID_LABEL);
            Label price = new Label("Price $/hr: "+room.getPrice().toString());
            price.setStyle(Style.LOW_LABEL);
            Button btn = UIUtils.createStyledButton("View Room", new SelectedRoomResult(room));
            resultVBox.getChildren().addAll(profilePic, name,price, btn);
            return resultVBox;
        }

        private class SelectedRoomResult implements EventHandler<ActionEvent> {

            private final Room room;

            public SelectedRoomResult(Room room){
                this.room = room;
            }

            @Override
            public void handle(ActionEvent event) {
                logger.info("Item selected {}", room.getName());
                searchResultsGraphicController.navigateToRoomResult(roomRenterResultBean, searchingView, searchingView instanceof SearchView, room);
            }
        }
    }

}
