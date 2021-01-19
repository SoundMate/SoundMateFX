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
import it.soundmate.model.User;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileView.class);

    public void updateEmail(EditGraphicController editGraphicController, User user, TextField textField, Label resultLabel) {
        try {
            editGraphicController.updateEmail(textField.getText(), user);
            updateUIStatus(resultLabel, "Email updated successfully", false);
        } catch (UpdateException updateException) {
            logger.error("Update Exception: {}", updateException.getMessage());
            updateUIStatus(resultLabel, "Something went wrong...", true);
        } catch (InputException inputException) {
            logger.error("Input Exception: {}", inputException.getMessage());
            updateUIStatus(resultLabel, "Invalid email", true);
        }
    }

    private void updateUIStatus(Label resultLabel, String message, boolean error) {
        resultLabel.setText(message);
        if (error) resultLabel.setStyle(Style.ERROR_TEXT);
        else resultLabel.setStyle(Style.FAV_GENRE_LABEL);
        resultLabel.setVisible(true);
    }

}
