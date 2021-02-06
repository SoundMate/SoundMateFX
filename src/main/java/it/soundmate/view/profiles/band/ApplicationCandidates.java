/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 05/02/21, 19:56
 * Last edited: 05/02/21, 19:56
 */

package it.soundmate.view.profiles.band;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.JoinRequest;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationCandidates extends ListView<JoinRequest> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationCandidates.class);
    private final BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();
    private final ProfileView profileView;
    private final Band band;
    private final Application application;

    public ApplicationCandidates(ProfileView profileView, Band band, Application application) {
        this.profileView = profileView;
        this.band = band;
        this.application = application;
        this.setCellFactory(param -> new ApplicationCandidate());
        this.setOrientation(Orientation.VERTICAL);
        this.setPrefHeight(150);
        this.setPrefWidth(525);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }

    public int length() {
        return this.getItems().size();
    }

    public class ApplicationCandidate extends ListCell<JoinRequest> {
        @Override
        protected void updateItem(JoinRequest joinRequest, boolean empty) {
            super.updateItem(joinRequest, empty);
            if (joinRequest != null) {
                HBox hBox = buildResultHBox(joinRequest);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(hBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private HBox buildResultHBox(JoinRequest joinRequest) {
            SoloResultBean soloResultBean;
            if (joinRequest.getSoloResultBean() == null) {
                soloResultBean = bandProfileGraphicController.getSoloFromJoinRequest(joinRequest);
            } else {
                soloResultBean = joinRequest.getSoloResultBean();
            }
            HBox resultHBox = new HBox();
            resultHBox.setAlignment(Pos.CENTER);
            resultHBox.setSpacing(10);
            Circle image = new Circle();
            image.setRadius(24);
            image.setFill(new ImagePattern(soloResultBean.getProfileImg()));
            Label nameLabel = new Label(soloResultBean.getFirstName()+" "+soloResultBean.getLastName());
            nameLabel.setStyle(Style.HIGH_LABEL);
            resultHBox.getChildren().addAll(image, nameLabel);
            UIUtils.addRegion(null, resultHBox);
            Button viewBtn = UIUtils.createStyledButton("View", new SelectedSoloResult(joinRequest));
            resultHBox.getChildren().add(viewBtn);
            return resultHBox;
        }

        private class SelectedSoloResult implements EventHandler<ActionEvent> {

            private final JoinRequest joinRequest;

            public SelectedSoloResult(JoinRequest joinRequest){
                this.joinRequest = joinRequest;
            }

            @Override
            public void handle(ActionEvent event) {
                logger.info("Item selected");
                profileView.setProfilePage(new JoinRequestDetailView(joinRequest, profileView, band, application));
            }
        }
    }

}
