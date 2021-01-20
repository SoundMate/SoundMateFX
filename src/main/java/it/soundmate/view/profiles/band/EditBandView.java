/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 21:48
 * Last edited: 20/01/21, 21:48
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Band;
import it.soundmate.utils.UserProperties;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.EditProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class EditBandView extends EditProfileView {

    private final Band band;
    private final ProfileView profileView;
    private final BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();

    //UI
    private final Rectangle rectangle = new Rectangle();
    private final TextField emailTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField nameTextField = new TextField();
    private final TextField cityTextField = new TextField();
    private final Label errorLabel = new Label();

    //Buttons
    private final Button editImageBtn = UIUtils.createStyledButton("Edit cover image", new AddCoverImageAction());
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final Button updateEmailBtn = UIUtils.createStyledButton(UPDATE, new EditBandAction(UserProperties.EMAIL));
    private final Button updatePasswordBtn = UIUtils.createStyledButton(UPDATE, new EditBandAction(UserProperties.PASSWORD));
    private final Button updateNameBtn = UIUtils.createStyledButton(UPDATE, new EditBandAction(UserProperties.NAME));
    private final Button updateCityBtn = UIUtils.createStyledButton(UPDATE, new EditBandAction(UserProperties.CITY));

    public EditBandView(Band band, ProfileView profileView) {
        this.band = band;
        this.profileView = profileView;
        this.setPadding(new Insets(0, 25, 25, 25));
        this.setSpacing(20);
        StackPane stackPane = buildStackPane(band, rectangle, editImageBtn);
        this.getChildren().add(stackPane);
        buildEditVBox(band);
        this.errorLabel.setVisible(false);
        this.getChildren().add(errorLabel);
    }

    private void buildEditVBox(Band band) {
        Label nameLabel = new Label(band.getBandName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        this.getChildren().add(nameLabel);

        HBox emailHBox = buildEditRow("Email: ", band.getEmail(), emailTextField, updateEmailBtn);
        HBox passwordHBox = buildEditRow("Password: ", band.getPassword(), passwordTextField, updatePasswordBtn);
        HBox nameHBox = buildEditRow("Name: ", band.getBandName(), nameTextField, updateNameBtn);
        HBox cityHBox = buildEditRow("City: ", band.getCity(), cityTextField, updateCityBtn);

        this.getChildren().addAll(emailHBox, passwordHBox, nameHBox, cityHBox, backBtn);
    }

    private class AddCoverImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            updateProfilePic(bandProfileGraphicController, band, rectangle);
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            bandProfileGraphicController.navigateToProfileView(profileView, band);
        }
    }

    private class EditBandAction implements EventHandler<ActionEvent> {

        private final UserProperties property;

        public EditBandAction(UserProperties property) {
            this.property = property;
        }

        @Override
        public void handle(ActionEvent event) {
            switch (property) {
                case EMAIL:
                    updateEmail(bandProfileGraphicController, band, emailTextField, errorLabel);
                    break;
                case PASSWORD:
                    updatePassword(bandProfileGraphicController, band, passwordTextField, errorLabel);
                    break;
                case NAME:
                    updateNameBand(bandProfileGraphicController, band, nameTextField, errorLabel);
                    break;
                case CITY:
                    updateCity(bandProfileGraphicController, band, cityTextField, errorLabel);
                    break;
                default:
                    throw new InputException("Non editable field clicked");
            }
        }
    }
}
