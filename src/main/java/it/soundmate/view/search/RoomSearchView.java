/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 28/01/21, 18:15
 * Last edited: 28/01/21, 18:15
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.model.Room;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RoomSearchView extends VBox {

    private final SearchingView searchingView;
    private final RoomRenterResultBean roomRenterResultBean;
    private final boolean comingFromSearch;
    private final Room room;

    //Buttons
    private final Button bookBtn = UIUtils.createStyledButton("Book room", new BookRoomAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public RoomSearchView(SearchingView searchingView, RoomRenterResultBean roomRenterResultBean, Room room, boolean comingFromSearch) {
        this.searchingView = searchingView;
        this.roomRenterResultBean = roomRenterResultBean;
        this.room = room;
        this.comingFromSearch = comingFromSearch;
        this.buildVBox();
    }

    private void buildVBox() {
        this.setPadding(new Insets(25));

        //Room image
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(500);
        rectangle.setHeight(170);
        if (room.getEncodedImg() != null) {
            rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getRoomPicFromCache(roomRenterResultBean.getId(), room.getCode()))));
        }
        this.getChildren().add(rectangle);

        //Name and desc
        Label name = new Label(room.getName());
        name.setStyle(Style.HEADER_TEXT);
        Label desc = new Label(room.getDescription());
        desc.setStyle(Style.MID_LABEL);
        this.getChildren().addAll(name, desc);

        //Book btn
        HBox bookHBox = new HBox();
        bookHBox.setAlignment(Pos.CENTER);
        bookHBox.getChildren().add(bookBtn);
        this.getChildren().addAll(bookHBox, backBtn);

    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setDetailViewRenter(new RenterSearchView(roomRenterResultBean, comingFromSearch, searchingView));
        }
    }

    private class BookRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }
}
