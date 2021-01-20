/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 00:08
 * Last edited: 19/01/21, 00:08
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.RoomRenterProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.Cache;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
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
    private final Button editEmailBtn = UIUtils.createStyledButton(UPDATE, new EditAction(0));
    private final Button editPasswordBtn = UIUtils.createStyledButton(UPDATE, new EditAction(1));
    private final Button editNameBtn = UIUtils.createStyledButton(UPDATE, new EditAction(2));
    private final Button editCityBtn = UIUtils.createStyledButton(UPDATE, new EditAction(3));
    private final Button editAddressBtn = UIUtils.createStyledButton(UPDATE, new EditAction(4));

    private final Rectangle rectangle = new Rectangle();
    private final StackPane stackPane;
    private final Button editImageBtn = UIUtils.createStyledButton("Edit cover image", new EditImageAction());
    private final Label errorLabel = new Label();

    public EditRenterView(RoomRenter roomRenter, ProfileView profileView) {
        this.roomRenter = roomRenter;
        this.profileView = profileView;
        this.stackPane = buildStackPane(roomRenter);
        buildEditVBox(roomRenter);
    }

    private StackPane buildStackPane(RoomRenter roomRenter) {
        StackPane imageStackPane = new StackPane();
        imageStackPane.setAlignment(Pos.CENTER);
        imageStackPane.setPrefWidth(USE_COMPUTED_SIZE);
        imageStackPane.setPrefHeight(USE_COMPUTED_SIZE);

        rectangle.setWidth(500);
        rectangle.setHeight(150);
        if (roomRenter.getEncodedImg() != null) {
            rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(roomRenter.getId()))));
        }
        imageStackPane.getChildren().addAll(rectangle, editImageBtn);
        return imageStackPane;
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

        //TextFields
        emailTextField.setStyle(Style.TEXT_FIELD);
        passwordTextField.setStyle(Style.TEXT_FIELD);
        nameTextField.setStyle(Style.TEXT_FIELD);
        cityTextField.setStyle(Style.TEXT_FIELD);
        addressTextField.setStyle(Style.TEXT_FIELD);

        buildEditRow("Email: ", roomRenter.getEmail(), emailTextField, editEmailBtn);
        buildEditRow("Password: ", roomRenter.getPassword(), passwordTextField, editPasswordBtn);
        buildEditRow("Name: ", roomRenter.getName(), nameTextField, editNameBtn);
        buildEditRow("City: ", roomRenter.getCity(), cityTextField, editCityBtn);
        buildEditRow("Address: ", roomRenter.getAddress(), addressTextField, editAddressBtn);

        this.getChildren().add(backBtn);

        //Error label
        HBox errorLabelHBox = new HBox();
        errorLabelHBox.setAlignment(Pos.CENTER);
        errorLabelHBox.setPrefHeight(USE_COMPUTED_SIZE);
        errorLabel.setVisible(false);
        errorLabel.setPadding(new Insets(5));
        errorLabelHBox.getChildren().add(errorLabel);
        this.getChildren().add(errorLabelHBox);
    }

    public void buildEditRow(String label, String text, TextField textField, Button button) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(USE_COMPUTED_SIZE);
        hBox.setPrefHeight(USE_COMPUTED_SIZE);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label actualLabel = new Label(label);
        actualLabel.setStyle(Style.MID_LABEL);
        Label textLabel = new Label(text);
        textLabel.setStyle(Style.MID_LABEL);

        hBox.getChildren().addAll(actualLabel, textLabel);
        UIUtils.addRegion(null, hBox);
        hBox.getChildren().addAll(textField, button);
        this.getChildren().add(hBox);
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Back clicked");
            profileView.setProfilePage(new RenterProfileView(profileView, roomRenter));
        }
    }

    private class EditAction implements EventHandler<ActionEvent> {

        private final int editType;

        public EditAction(int editType) {
            this.editType = editType;
        }

        @Override
        public void handle(ActionEvent event) {
            switch (editType) {
                case 0:
                    logger.info("Edit email");
                    updateEmail(roomRenterProfileGraphicController, roomRenter, emailTextField, errorLabel);
                    break;
                case 1:
                    logger.info("Edit password");
                    updatePassword(roomRenterProfileGraphicController, roomRenter, passwordTextField, errorLabel);
                    break;
                case 2:
                    logger.info("Edit name");
                    updateNameRoomRenter(roomRenterProfileGraphicController, roomRenter, nameTextField, errorLabel);
                    break;
                case 3:
                    logger.info("Edit city");
                    updateCity(roomRenterProfileGraphicController, roomRenter, cityTextField, errorLabel);
                    break;
                case 4:
                    logger.info("Edit address");
                    updateAddress(roomRenterProfileGraphicController, roomRenter, addressTextField, errorLabel);
                    break;
                default:
                    throw new InputException("No edit field found");
            }
        }

    }

    private class EditImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit image click");
            try {
                roomRenterProfileGraphicController.updateProfilePic(rectangle, roomRenter);
                rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(roomRenter.getId()))));
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            } catch (UpdateException updateException) {
                logger.error("Update Exception: {}", updateException.getMessage());
            }
        }
    }
}
