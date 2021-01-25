/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 22/01/21, 15:40
 * Last edited: 22/01/21, 15:40
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.search.SearchResultsGraphicController;
import it.soundmate.database.dbexceptions.RepositoryException;
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

public class RenterResults extends ListView<RoomRenterResultBean> {
    private static final Logger logger = LoggerFactory.getLogger(RenterResults.class);
    private final SearchResultsGraphicController searchResultsGraphicController = new SearchResultsGraphicController();
    private final SearchingView searchingView;

    public RenterResults(SearchingView searchingView) {
        this.searchingView = searchingView;
        this.setCellFactory(param -> new RenterResult());
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

    public class RenterResult extends ListCell<RoomRenterResultBean> {
        @Override
        protected void updateItem(RoomRenterResultBean renter, boolean empty) {
            super.updateItem(renter, empty);
            if (renter != null) {
                VBox vBox = buildResultVBox(renter);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(vBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private VBox buildResultVBox(RoomRenterResultBean renter) {
            VBox resultVBox = ResultsView.buildResultVBox();
            Circle profilePic = new Circle();
            profilePic.setRadius(25);
            if (renter.getProfileImgIs() != null) {
                ImagePattern imagePattern = new ImagePattern(renter.getProfileImg());
                profilePic.setFill(imagePattern);
            }
            Label name = new Label(renter.getName());
            name.setStyle(Style.MID_LABEL);
            Button btn = UIUtils.createStyledButton("View Profile", new SelectedRenterAction(renter));
            resultVBox.getChildren().addAll(profilePic, name, btn);
            return resultVBox;
        }

        private class SelectedRenterAction implements EventHandler<ActionEvent> {

            private final RoomRenterResultBean renter;

            public SelectedRenterAction(RoomRenterResultBean renter){
                this.renter = renter;
            }

            @Override
            public void handle(ActionEvent event) {
                logger.info("Item selected {}", renter.getName());
                try {
                    renter.setRooms(searchResultsGraphicController.fetchRenterData(renter));
                    searchResultsGraphicController.navigateToRenterResult(renter, searchingView, searchingView instanceof SearchView);
                } catch (RepositoryException repositoryException) {
                    logger.error("Repository Exception: {}", repositoryException.getMessage());
                }
            }
        }
    }
}
