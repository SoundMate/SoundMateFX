/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 20:56
 * Last edited: 19/01/21, 20:56
 */

package it.soundmate.view.profiles;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.EditGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileView.class);
    protected static final String UPDATE = "Update";
    protected static final String UPDATE_EXCEPTION = "Update Exception: {}";
    protected static final String SOMETHING_WENT_WRONG = "Something went wrong...";
    protected static final String INPUT_EXCEPTION = "Input Exception: {}";

    protected StackPane buildStackPane(User user, Rectangle rectangle, Button editImageBtn) {
        StackPane imageStackPane = new StackPane();
        imageStackPane.setAlignment(Pos.CENTER);
        imageStackPane.setPrefWidth(USE_COMPUTED_SIZE);
        imageStackPane.setPrefHeight(USE_COMPUTED_SIZE);

        rectangle.setWidth(500);
        rectangle.setHeight(150);
        if (user.getEncodedImg() != null) {
            rectangle.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(user.getId()))));
        }
        imageStackPane.getChildren().addAll(rectangle, editImageBtn);
        return imageStackPane;
    }

    protected HBox buildEditRow(String label, String text, TextField textField, Button button) {
        HBox hBox = new HBox();
        hBox.setPrefWidth(USE_COMPUTED_SIZE);
        hBox.setPrefHeight(USE_COMPUTED_SIZE);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        Label actualLabel = new Label(label);
        actualLabel.setStyle(Style.MID_LABEL);
        Label textLabel = new Label(text);
        textLabel.setStyle(Style.MID_LABEL);
        textField.setStyle(Style.TEXT_FIELD);

        hBox.getChildren().addAll(actualLabel, textLabel);
        UIUtils.addRegion(null, hBox);
        hBox.getChildren().addAll(textField, button);
        return hBox;
    }


    protected void updateEmail(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateEmail(textField.getText(), user);
            updateUIStatus(resultLabel, "Email updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid email", true);
        }
    }

    protected void updateNameRoomRenter(EditGraphicController editGraphicController, RoomRenter roomRenter, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateNameRoomRenter(textField.getText(), roomRenter);
            updateUIStatus(resultLabel, "Name updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid name", true);
        }
    }

    protected void updateNameBand(EditGraphicController editGraphicController, Band band, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateNameBand(textField.getText(), band);
            updateUIStatus(resultLabel, "Name updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid name", true);
        }
    }

    protected void updatePassword(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updatePassword(textField.getText(), user);
            updateUIStatus(resultLabel, "Password updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Password must be > 5 characters", true);
        }
    }

    protected void updateCity(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateCity(textField.getText(), user);
            updateUIStatus(resultLabel, "City updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid city", true);
        }
    }

    protected void updateAddress(EditGraphicController editGraphicController, RoomRenter roomRenter, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateAddress(textField.getText(), roomRenter);
            updateUIStatus(resultLabel, "Address updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid address", true);
        }
    }

    protected void updateProfilePic(EditGraphicController editGraphicController, User user, Shape shape) {
        try {
            editGraphicController.updateProfilePic(shape, user);
            shape.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(user.getId()))));
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
        }
    }

    protected void updateFirstName(EditGraphicController editGraphicController, Solo solo, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateFirstName(textField.getText(), solo);
            updateUIStatus(resultLabel, "First Name updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid first name", true);
        }
    }

    protected void updateLastName(EditGraphicController editGraphicController, Solo solo, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateLastName(textField.getText(), solo);
            updateUIStatus(resultLabel, "Last Name updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error(UPDATE_EXCEPTION, updateException.getMessage());
            updateUIStatus(resultLabel, SOMETHING_WENT_WRONG, true);
        } catch (InputException inputException) {
            logger.error(INPUT_EXCEPTION, inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid last name", true);
        }
    }

    protected void updateUIStatus(Label resultLabel, String message, boolean error) {
        resultLabel.setText(message);
        if (error) resultLabel.setStyle(Style.ERROR_TEXT);
        else resultLabel.setStyle(Style.FAV_GENRE_LABEL);
        resultLabel.setVisible(true);
    }

}
