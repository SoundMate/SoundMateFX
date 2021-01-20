/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:07
 * Last edited: 18/01/21, 18:07
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.model.RoomRenter;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.renter.EditRenterView;


public class RoomRenterProfileGraphicController extends EditGraphicController {

    public void navigateToEditView(ProfileView profileView, RoomRenter roomRenter) {
        profileView.setProfilePage(new EditRenterView(roomRenter, profileView));
    }

}
