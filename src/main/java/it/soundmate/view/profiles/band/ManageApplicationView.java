/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 05/02/21, 12:00
 * Last edited: 05/02/21, 12:00
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.ApplicationController;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.JoinRequest;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.List;

public class ManageApplicationView extends VBox {

    private final ProfileView profileView;
    private final Band band;

    //UI
    private final ApplicationCandidates applicationCandidates;
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final ApplicationController applicationController = new ApplicationController();

    public ManageApplicationView(ProfileView profileView, Band band, Application application) {
        this.profileView = profileView;
        this.band = band;
        this.applicationCandidates = new ApplicationCandidates(profileView, band, application);
        buildContentVBox(application);
    }

    private void buildContentVBox(Application application) {
        this.setPadding(new Insets(25));
        this.setSpacing(10);
        Label applicationID = new Label("Application #"+application.getApplicationCode());
        applicationID.setStyle(Style.HEADER_TEXT);
        Label messageLabel = new Label("Message: "+application.getMessage());
        messageLabel.setStyle(Style.HIGH_LABEL);
        Label candidatesLabel = new Label("Applied Solos");
        candidatesLabel.setStyle(Style.HIGH_LABEL);
        this.getChildren().addAll(applicationID, messageLabel);
        HBox hBox = buildInstrumentsHBox(application);
        this.getChildren().add(hBox);
        this.getChildren().add(candidatesLabel);
        Label candidatesMessages = new Label("");
        candidatesMessages.setStyle(Style.LOW_LABEL);
        this.getChildren().add(candidatesMessages);
        List<JoinRequest> joinRequestList = applicationController.getJoinRequests(application);
        ObservableList<JoinRequest> joinRequestObservableList = FXCollections.observableArrayList(joinRequestList);
        applicationCandidates.setItems(joinRequestObservableList);
        applicationCandidates.setStyle("-fx-background-color: #232323; -fx-border-color: #232323");
        this.getChildren().add(applicationCandidates);
        this.getChildren().add(backBtn);
    }

    private HBox buildInstrumentsHBox(Application application) {
        HBox instrumentsHBox = new HBox();
        instrumentsHBox.setSpacing(10);
        for (String instrument : application.getInstrumentsList()) {
            VBox instrumentVBox = buildInstrumentVBox(instrument);
            instrumentsHBox.getChildren().add(instrumentVBox);
        }
        return instrumentsHBox;
    }

    public static VBox buildInstrumentVBox(String instrument) {
        InstrumentGraphics instrumentGraphics = InstrumentGraphics.returnInstrument(instrument);
        Circle image = new Circle();
        image.setRadius(24);
        image.setFill(new ImagePattern(instrumentGraphics.getSource()));
        Label instrumentLabel = new Label(instrumentGraphics.getName());
        instrumentLabel.setStyle(Style.MID_LABEL);
        VBox instrumentVBox = new VBox();
        instrumentVBox.setSpacing(10);
        instrumentVBox.setAlignment(Pos.CENTER);
        instrumentVBox.getChildren().addAll(image, instrumentLabel);
        return instrumentVBox;
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new BandProfileView(profileView, band));
        }
    }
}
