/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 05/02/21, 01:35
 * Last edited: 05/02/21, 01:35
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.controller.graphic.profiles.SoloProfileGraphicController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class NewApplicationView extends VBox {

    private final List<String> instrumentsList = new ArrayList<>();
    private final ProfileView profileView;
    private final Band band;
    private final BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();

    //UI
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final HBox instrumentsHBox = new HBox();
    private final Button addInstrumentBtn = UIUtils.createStyledButton("Add instrument", new AddInstrumentAction());
    private final TextArea messageTextArea = new TextArea();
    private final Button createApplicationBtn = UIUtils.createStyledButton("Create Application", new CreateApplicationAction());

    public NewApplicationView(ProfileView profileView, Band band) {
        this.profileView = profileView;
        this.band = band;
        buildContentVBox();
    }

    private void buildContentVBox() {
        this.setSpacing(10);
        this.setPadding(new Insets(25));
        Label title = new Label("New Application");
        title.setStyle(Style.HEADER_TEXT);
        Label instrumentsLabel = new Label("Looking for: ");
        instrumentsLabel.setStyle(Style.HIGH_LABEL);
        Label messageLabel = new Label("Add a message");
        messageLabel.setStyle(Style.HIGH_LABEL);
        this.getChildren().add(title);
        this.instrumentsHBox.getChildren().addAll(instrumentsLabel, addInstrumentBtn);
        this.getChildren().add(this.instrumentsHBox);
        this.getChildren().add(messageLabel);
        this.getChildren().add(messageTextArea);
        HBox buttons = new HBox();
        buttons.getChildren().add(backBtn);
        UIUtils.addRegion(null, buttons);
        buttons.getChildren().add(createApplicationBtn);
        this.getChildren().add(buttons);
    }

    private class AddInstrumentAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            InstrumentGraphics instrumentGraphics = SoloProfileGraphicController.instrumentDialog();
            if (instrumentGraphics != null) {
                VBox instrumentVBox = new VBox();
                instrumentVBox.setAlignment(Pos.CENTER);
                instrumentVBox.setSpacing(10);
                Label instrumentName = new Label(instrumentGraphics.getName());
                instrumentName.setStyle(Style.MID_LABEL);
                Circle image = new Circle();
                image.setRadius(24);
                image.setFill(new ImagePattern(instrumentGraphics.getSource()));
                instrumentVBox.getChildren().addAll(image, instrumentName);
                instrumentsHBox.getChildren().add(instrumentVBox);
                instrumentsList.add(instrumentGraphics.getName());
            }
        }
    }

    private class CreateApplicationAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application application = new Application();
            application.setBand(band.getId());
            application.setInstrumentsList(instrumentsList);
            application.setMessage(messageTextArea.getText());
            try {
                bandProfileGraphicController.addApplication(application);
                Alert confirmedDialog = new Alert(Alert.AlertType.INFORMATION);
                confirmedDialog.setTitle("Application has been created");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.showAndWait();
            } catch (RepositoryException repositoryException) {
                Alert confirmedDialog = new Alert(Alert.AlertType.ERROR);
                confirmedDialog.setTitle("Application has not been created");
                confirmedDialog.setHeaderText(null);
                confirmedDialog.setContentText(repositoryException.getMessage());
                confirmedDialog.showAndWait();
            }
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new BandProfileView(profileView, band));
        }
    }
}
