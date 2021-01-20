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
import it.soundmate.model.RoomRenter;
import it.soundmate.model.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileView.class);
    public static final String UPDATE_EXCEPTION = "Update Exception: {}";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong...";
    public static final String INPUT_EXCEPTION = "Input Exception: {}";

    public void updateEmail(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
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

    public void updateNameRoomRenter(EditGraphicController editGraphicController, RoomRenter roomRenter, TextField textField, Label resultLabel) {
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

    public void updateCity(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
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

    private void updateUIStatus(Label resultLabel, String message, boolean error) {
        resultLabel.setText(message);
        if (error) resultLabel.setStyle(Style.ERROR_TEXT);
        else resultLabel.setStyle(Style.FAV_GENRE_LABEL);
        resultLabel.setVisible(true);
    }

}
