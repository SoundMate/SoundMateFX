/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 21:30
 * Last edited: 25/01/21, 21:30
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.model.Room;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchView;
import it.soundmate.view.main.SearchingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class RenterSearchView extends Pane {

    private final RoomRenterResultBean roomRenterResultBean;
    private final boolean comingFromSearch;
    private final SearchingView searchingView;

    //UI
    private VBox mainVBox = new VBox();  //Questo vbox è quello da cambiare per cambiare pagina
    private final VBox contentVBox = new VBox();
    private final VBox infoVBox = new VBox();
    private final HBox infoHBox = new HBox();
    private final RoomResults roomResults;

    //Buttons
    private final Button viewOnMapBtn = UIUtils.createStyledButton("View on map", new ViewOnMapAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public RenterSearchView(RoomRenterResultBean renterResultBean, boolean comingFromSearch, SearchingView searchingView) {
        this.searchingView = searchingView;
        this.comingFromSearch = comingFromSearch;
        this.roomRenterResultBean = renterResultBean;
        this.setPrefHeight(USE_COMPUTED_SIZE);
        UIUtils.setBackgroundPane("#232323", this);
        this.roomResults = new RoomResults(renterResultBean.getId(), searchingView, roomRenterResultBean);
        this.buildVBox();
    }

    private void buildVBox() {
        this.contentVBox.setAlignment(Pos.CENTER);
        //Profile picture
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(600);
        rectangle.setHeight(175);
        if (roomRenterResultBean.getProfileImg() != null) {
            rectangle.setFill(new ImagePattern(roomRenterResultBean.getProfileImg()));
        }
        this.contentVBox.getChildren().add(rectangle);
        this.infoVBox.setPadding(new Insets(25));
        //User info
        buildInfobox();
        //Rooms
        buildRoomsHBox();
        UIUtils.addSizedRegion(this, 40, 40);
        this.contentVBox.getChildren().add(this.backBtn);
        this.mainVBox.getChildren().add(this.contentVBox);
    }

    private void buildRoomsHBox() {
        Label rooms = new Label("Rooms");
        rooms.setPadding(new Insets(0, 0, 0, 25));
        rooms.setStyle(Style.MID_LABEL);
        this.contentVBox.getChildren().add(rooms);

        roomResults.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        List<Room> roomList = new ArrayList<>(roomRenterResultBean.getRooms());
        ObservableList<Room> roomObservableList = FXCollections.observableArrayList(roomList);
        roomResults.setItems(roomObservableList);
        this.contentVBox.getChildren().add(roomResults);
    }

    private void buildInfobox() {
        Label nameLabel = new Label(roomRenterResultBean.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        this.infoVBox.getChildren().add(nameLabel);

        Label cityAndAddressLabel = new Label(roomRenterResultBean.getCity()+", "+roomRenterResultBean.getAddress());
        cityAndAddressLabel.setStyle(Style.MID_LABEL);
        this.infoHBox.getChildren().add(cityAndAddressLabel);
        UIUtils.addRegion(null, this.infoHBox);
        this.infoHBox.getChildren().add(viewOnMapBtn);
        this.infoVBox.getChildren().add(this.infoHBox);
        this.contentVBox.getChildren().add(this.infoVBox);
    }

    public void setMapView() {
        this.mainVBox.getChildren().set(0, new RenterMapView(roomRenterResultBean, searchingView, searchingView instanceof SearchView));
    }

    public VBox getMainVBox() {
        return mainVBox;
    }

    private class ViewOnMapAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setMapView();
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (comingFromSearch) searchingView.backToSearchView();
            else searchingView.backToHomeView();
        }
    }
}
