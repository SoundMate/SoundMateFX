/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 22/01/21, 15:52
 * Last edited: 22/01/21, 15:52
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.search.SearchResultsGraphicController;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BandResults extends ListView<BandResultBean> {

    private static final Logger logger = LoggerFactory.getLogger(BandResults.class);
    private final SearchResultsGraphicController searchResultsGraphicController = new SearchResultsGraphicController();
    private final SearchingView searchingView;

    public BandResults(SearchingView searchingView) {
        this.searchingView = searchingView;
        this.setCellFactory(param -> new BandResult());
        this.setOrientation(Orientation.HORIZONTAL);
        this.setPrefHeight(150);
        this.setPrefWidth(525);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class BandResult extends ListCell<BandResultBean> {
        @Override
        protected void updateItem(BandResultBean band, boolean empty) {
            super.updateItem(band, empty);
            if (band != null) {
                VBox vBox = buildResultVBox(band);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(vBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private VBox buildResultVBox(BandResultBean band) {
            VBox resultVBox = ResultsView.buildResultVBox();
            Circle profilePic = new Circle();
            profilePic.setRadius(25);
            if (band.getProfileImgIs() != null) {
                ImagePattern imagePattern = new ImagePattern(band.getProfileImg());
                profilePic.setFill(imagePattern);
            }
            Label name = new Label(band.getBandName());
            name.setStyle(Style.MID_LABEL);
            Button btn = UIUtils.createStyledButton("View Profile", new SelectedBandAction(band));
            resultVBox.getChildren().addAll(profilePic, name, btn);
            return resultVBox;
        }

        private class SelectedBandAction implements EventHandler<ActionEvent> {

            private final BandResultBean band;

            public SelectedBandAction(BandResultBean renter){
                this.band = renter;
            }

            @Override
            public void handle(ActionEvent event) {
                logger.info("Item selected {}", band.getBandName());
                searchResultsGraphicController.naviagateToBandResult(band, searchingView, searchingView instanceof SearchView);

            }
        }
    }
}
