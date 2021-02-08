/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 26/01/21, 15:12
 * Last edited: 26/01/21, 15:12
 */

package it.soundmate.view.search;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//GMaps FX Prova
/*
* Il controller restituisce lat e long della posizione
* (da usare thread), si devono mostrare su una mappa
* */

public class RenterMapView extends VBox implements MapComponentInitializedListener {

    private static final Logger logger = LoggerFactory.getLogger(RenterMapView.class);
    private final RoomRenterProfileGraphicController renterProfileGraphicController = new RoomRenterProfileGraphicController();
    private final RoomRenterResultBean roomRenterResultBean;
    private final SearchingView searchingView;
    private final boolean comingFromSearch;

    private final GoogleMapView googleMapView = new GoogleMapView();
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public RenterMapView(RoomRenterResultBean roomRenterResultBean, SearchingView searchingView, boolean comingFromSearch) {
        this.roomRenterResultBean = roomRenterResultBean;
        this.searchingView = searchingView;
        this.comingFromSearch = comingFromSearch;
        this.googleMapView.addMapInializedListener(this);
        this.buildMapVBox(roomRenterResultBean);
    }

    private void buildMapVBox(RoomRenterResultBean roomRenterResultBean) {
        Label nameLabel = new Label(roomRenterResultBean.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameLabel.setPadding(new Insets(25, 0, 25, 25));
        this.getChildren().add(nameLabel);


        VBox webVBox = new VBox();
        webVBox.setPrefHeight(500);
        webVBox.setPrefWidth(600);
        webVBox.getChildren().add(googleMapView);
        try {
            renterProfileGraphicController.displayMap(roomRenterResultBean.getCity(), roomRenterResultBean.getAddress());
        } catch (InputException inputException) {
            logger.error("Input Exception: {}", inputException.getMessage());
            webEngine.loadContent("Map not available", "text/html");
        }
        this.getChildren().add(webVBox);

        HBox padding = new HBox(this.backBtn);
        padding.setPadding(new Insets(25));
        this.getChildren().add(padding);
    }

    @Override
    public void mapInitialized() {
        LatLong latLong = new LatLong(41.9102415, 12.3959136);
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(latLong)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        //UI
        GoogleMap map = googleMapView.createMap(mapOptions);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLong);
        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>ROMA</h2>Location di Roma<br>");
        InfoWindow infoWindow = new InfoWindow(infoWindowOptions);
        infoWindow.open(map, marker);
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setDetailViewRenter(new RenterSearchView(roomRenterResultBean, comingFromSearch, searchingView));
        }
    }
}
