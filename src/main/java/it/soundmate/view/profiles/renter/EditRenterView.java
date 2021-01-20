/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 00:08
 * Last edited: 19/01/21, 00:08
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.UserProperties;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.EditProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditRenterView extends EditProfileView {

    public static final String UPDATE = "Update";
    private final RoomRenter roomRenter;
    private final ProfileView profileView;
    private static final Logger logger = LoggerFactory.getLogger(EditRenterView.class);
    private final RoomRenterProfileGraphicController roomRenterProfileGraphicController = new RoomRenterProfileGraphicController();

    //UI
    private final TextField emailTextField = new TextField();
    private final TextField nameTextField = new TextField();
    private final TextField passwordTextField = new TextField();
    private final TextField cityTextField = new TextField();
    private final TextField addressTextField = new TextField();

    //Buttons
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final Button editEmailBtn = UIUtils.createStyledButton(UPDATE, new EditRenterAction(UserProperties.EMAIL));
    private final Button editPasswordBtn = UIUtils.createStyledButton(UPDATE, new EditRenterAction(UserProperties.PASSWORD));
    private final Button editNameBtn = UIUtils.createStyledButton(UPDATE, new EditRenterAction(UserProperties.NAME));
    private final Button editCityBtn = UIUtils.createStyledButton(UPDATE, new EditRenterAction(UserProperties.CITY));
    private final Button editAddressBtn = UIUtils.createStyledButton(UPDATE, new EditRenterAction(UserProperties.ADDRESS));

    private final Rectangle rectangle = new Rectangle();
    private final StackPane stackPane;
    private final Label errorLabel = new Label();

    public EditRenterView(RoomRenter roomRenter, ProfileView profileView) {
        this.roomRenter = roomRenter;
        this.profileView = profileView;
        Button editImageBtn = UIUtils.createStyledButton("Edit cover image", new EditImageAction());
        this.stackPane = buildStackPane(roomRenter, rectangle, editImageBtn);
        buildEditVBox(roomRenter);
    }

    private void buildEditVBox(RoomRenter roomRenter) {
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(35));
        this.getChildren().add(this.stackPane);

        Label nameLabel = new Label(roomRenter.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        this.getChildren().add(nameLabel);
        UIUtils.addRegion(this, null);

        HBox emailHBox = buildEditRow("Email: ", roomRenter.getEmail(), emailTextField, editEmailBtn);
        HBox passwordHBox =buildEditRow("Password: ", roomRenter.getPassword(), passwordTextField, editPasswordBtn);
        HBox nameHBox =buildEditRow("Name: ", roomRenter.getName(), nameTextField, editNameBtn);
        HBox cityHBox =buildEditRow("City: ", roomRenter.getCity(), cityTextField, editCityBtn);
        HBox addressHBox =buildEditRow("Address: ", roomRenter.getAddress(), addressTextField, editAddressBtn);

        this.getChildren().addAll(emailHBox, passwordHBox, nameHBox, cityHBox, addressHBox, backBtn);

        //Error label
        HBox errorLabelHBox = new HBox();
        errorLabelHBox.setAlignment(Pos.CENTER);
        errorLabelHBox.setPrefHeight(USE_COMPUTED_SIZE);
        errorLabel.setVisible(false);
        errorLabel.setPadding(new Insets(5));
        errorLabelHBox.getChildren().add(errorLabel);
        this.getChildren().add(errorLabelHBox);
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Back clicked");
            profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
        }
    }

    private class EditImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit image click");
            updateProfilePic(roomRenterProfileGraphicController, roomRenter, rectangle);
        }
    }

    private class EditRenterAction implements EventHandler<ActionEvent> {
        private final UserProperties property;

        public EditRenterAction(UserProperties properties) {
            this.property = properties;
        }

        @Override
        public void handle(ActionEvent event) {
            switch (property) {
                case EMAIL:
                    updateEmail(roomRenterProfileGraphicController, roomRenter, emailTextField, errorLabel);
                    break;
                case PASSWORD:
                    updatePassword(roomRenterProfileGraphicController, roomRenter, passwordTextField, errorLabel);
                    break;
                case NAME:
                    updateNameRoomRenter(roomRenterProfileGraphicController, roomRenter, nameTextField, errorLabel);
                    break;
                case CITY:
                    updateCity(roomRenterProfileGraphicController, roomRenter, cityTextField, errorLabel);
                    break;
                case ADDRESS:
                    updateAddress(roomRenterProfileGraphicController, roomRenter, addressTextField, errorLabel);
                    break;
                default:
                    throw new InputException("Non editable field clicked");
            }
        }
    }
}
