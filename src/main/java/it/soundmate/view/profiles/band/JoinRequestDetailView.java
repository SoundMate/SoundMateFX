/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 06/02/21, 12:22
 * Last edited: 06/02/21, 12:22
 */

package it.soundmate.view.profiles.band;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.controller.logic.ApplicationController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.JoinRequest;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JoinRequestDetailView extends VBox {

    private final JoinRequest joinRequest;
    private final ProfileView profileView;
    private final Band band;
    private final Application application;

    //UI
    private final Button acceptBtn = UIUtils.createStyledButton("Accept", new AcceptAction());
    private final Button declineBtn = UIUtils.createStyledButton("Decline", new DeclineAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private static final Logger logger = LoggerFactory.getLogger(JoinRequestDetailView.class);

    public JoinRequestDetailView(JoinRequest joinRequest, ProfileView profileView, Band band, Application application) {
        this.joinRequest = joinRequest;
        this.profileView = profileView;
        this.band = band;
        this.application = application;
        buildContentVBox();
    }

    private void buildContentVBox() {
        SoloResultBean soloResultBean;
        if (joinRequest.getSoloResultBean() != null) soloResultBean = joinRequest.getSoloResultBean();
        else {
            BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();
            soloResultBean = bandProfileGraphicController.getSoloFromJoinRequest(joinRequest);
        }
        this.setPadding(new Insets(25));
        this.setSpacing(10);
        Label title = new Label("Join Request from: "+soloResultBean.getFirstName()+" "+soloResultBean.getLastName());
        title.setStyle(Style.HEADER_TEXT);
        Label instrumentsPlayed = new Label("Instruments Played");
        instrumentsPlayed.setStyle(Style.HIGH_LABEL);
        this.getChildren().addAll(title, instrumentsPlayed);
        HBox instrumentsPlayedHBox = buildInstrumentsHBox(soloResultBean);
        this.getChildren().add(instrumentsPlayedHBox);
        Label messageLabel = new Label("Message");
        messageLabel.setStyle(Style.HIGH_LABEL);
        this.getChildren().add(messageLabel);
        Label messageContentLabel = new Label(joinRequest.getMessage());
        messageContentLabel.setStyle(Style.MID_LABEL);
        this.getChildren().add(messageContentLabel);
        HBox buttonsHBox = new HBox();
        buttonsHBox.getChildren().add(acceptBtn);
        UIUtils.addRegion(null, buttonsHBox);
        declineBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        buttonsHBox.getChildren().add(declineBtn);
        this.getChildren().add(buttonsHBox);
        this.getChildren().add(backBtn);
    }

    private HBox buildInstrumentsHBox(SoloResultBean soloResultBean) {
        HBox instrumentsHBox = new HBox();
        instrumentsHBox.setSpacing(10);
        instrumentsHBox.setAlignment(Pos.CENTER);
        for (String instrument: soloResultBean.getInstrumentList()) {
            instrumentsHBox.getChildren().add(ManageApplicationView.buildInstrumentVBox(instrument));
        }
        return instrumentsHBox;
    }


    private class AcceptAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                ApplicationController applicationController = new ApplicationController();
                applicationController.acceptRequest(joinRequest);
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setContentText("Request accepted");
                confirmation.showAndWait();
            } catch (RepositoryException repositoryException) {
                logger.error("Request not accepted: {}", repositoryException.getMessage());
            }
        }
    }

    private static class DeclineAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            //Decline action
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new ManageApplicationView(profileView, band, application));
        }
    }
}
