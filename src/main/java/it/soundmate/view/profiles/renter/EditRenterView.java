/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 00:08
 * Last edited: 19/01/21, 00:08
 */

package it.soundmate.view.profiles.renter;

import it.soundmate.constants.Style;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.RoomRenter;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditRenterView extends VBox {

    public static final String UPDATE = "Update";
    private final RoomRenter roomRenter;
    private final ProfileView profileView;
    private static final Logger logger = LoggerFactory.getLogger(EditRenterView.class);

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

    public EditRenterView(RoomRenter roomRenter, ProfileView profileView) {
        this.roomRenter = roomRenter;
        this.profileView = profileView;
        buildEditVBox(roomRenter);
    }

    private void buildEditVBox(RoomRenter roomRenter) {
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(20);
        this.setPadding(new Insets(35));

        Label nameLabel = new Label(roomRenter.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        this.getChildren().addAll(backBtn, nameLabel);
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
                    break;
                case 1:
                    logger.info("Edit password");
                    break;
                case 2:
                    logger.info("Edit name");
                    break;
                case 3:
                    logger.info("Edit city");
                    break;
                case 4:
                    logger.info("Edit address");
                    break;
                default:
                    throw new InputException("No edit field found");
            }
        }
    }
}
