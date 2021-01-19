/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 20:24
 * Last edited: 19/01/21, 20:24
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.controller.logic.profiles.EditController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
import it.soundmate.utils.ImagePicker;
import javafx.scene.shape.Shape;

import java.io.File;
import java.io.IOException;

public class EditGraphicController {

    private final EditController editController = new EditController();

    public void updateProfilePic(Shape shape, User user){
        ImagePicker imagePicker = new ImagePicker();
        File chosenImg = imagePicker.chooseImage(shape);
        if (chosenImg == null) {
            throw new InputException("Image not chosen");
        } else {
            try {
                if (!Cache.getInstance().saveProfilePicToCache(user, chosenImg)) throw new InputException("Error saving img in cache");
                editController.updateProfilePic(chosenImg, user);
            } catch (UpdateException updateException) {
                throw new UpdateException(updateException.getMessage());
            } catch (IOException ioException) {
                throw new InputException(ioException.getMessage());
            }
        }
    }

    public void updateEmail(String email, User user) {
        if ("".equals(email) || !email.contains("@")) throw new InputException("Invalid email");
        try {
            editController.updateEmail(email, user);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updatePassword(String password, User user) {
        if (password.length() < 5) throw new InputException("Password length must be at least 5 characters");
        try {
            editController.updatePassword(password, user);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }
}
