/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:07
 * Last edited: 18/01/21, 18:07
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.controller.logic.profiles.RoomRenterProfileController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.Cache;
import it.soundmate.utils.ImagePicker;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class RoomRenterProfileGraphicController {

    private final RoomRenterProfileController roomRenterProfileController = new RoomRenterProfileController();
    private static final Logger logger = LoggerFactory.getLogger(RoomRenterProfileGraphicController.class);

    public void addCoverImage(Rectangle rectangle, RoomRenter roomRenter){
        ImagePicker imagePicker = new ImagePicker();
        File chosenImg = imagePicker.chooseImage(rectangle);
        if (chosenImg == null) {
            throw new InputException("Image not chosen");
        } else {
            try {
                if (!Cache.getInstance().saveProfilePicToCache(roomRenter, chosenImg)) throw new InputException("Error saving img in cache");
                roomRenterProfileController.addCoverImage(chosenImg, roomRenter);
            } catch (UpdateException updateException) {
                throw new UpdateException(updateException.getMessage());
            } catch (IOException ioException) {
                throw new InputException(ioException.getMessage());
            }
        }
    }

}
