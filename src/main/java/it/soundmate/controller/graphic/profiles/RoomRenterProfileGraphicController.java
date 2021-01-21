/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:07
 * Last edited: 18/01/21, 18:07
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.bean.AddRoomBean;
import it.soundmate.controller.logic.profiles.RoomRenterProfileController;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.renter.EditRenterView;


public class RoomRenterProfileGraphicController extends EditGraphicController {

    private final RoomRenterProfileController roomRenterProfileController = new RoomRenterProfileController();

    public void navigateToEditView(ProfileView profileView, RoomRenter roomRenter) {
        profileView.setProfilePage(new EditRenterView(roomRenter, profileView));
    }

    public int addRoom(AddRoomBean addRoomBean, RoomRenter roomRenter) {
        try {
            return roomRenterProfileController.addRoom(addRoomBean, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }
}
