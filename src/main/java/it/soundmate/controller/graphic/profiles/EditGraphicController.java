/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 20:24
 * Last edited: 19/01/21, 20:24
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.controller.logic.profiles.EditController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
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

    public void updateNameRoomRenter(String name, RoomRenter roomRenter) {
        if ("".equals(name)) throw new InputException("Name is empty");
        try {
            editController.updateNameRoomRenter(name, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateNameBand(String name, Band band) {
        if ("".equals(name)) throw new InputException("Name is empty");
        try {
            editController.updateNameBand(name, band);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateCity(String city, User user) {
        if ("".equals(city)) throw new InputException("City is empty");
        try {
            editController.updateCity(city, user);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateAddress(String address, RoomRenter roomRenter) {
        if ("".equals(address)) throw new InputException("Address is empty");
        try {
            editController.updateAddress(address, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateFirstName(String firstName, Solo solo) {
        if ("".equals(firstName)) throw new InputException("First name is empty");
        try {
            editController.updateFirstName(firstName, solo);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateLastName(String lastName, Solo solo) {
        if ("".equals(lastName)) throw new InputException("Last name is empty");
        try {
            editController.updateLastName(lastName, solo);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }
}
