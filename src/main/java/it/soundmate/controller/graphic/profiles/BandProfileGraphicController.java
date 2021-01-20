/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 21:37
 * Last edited: 20/01/21, 21:37
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.model.Band;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.band.BandProfileView;
import it.soundmate.view.profiles.band.EditBandView;

public class BandProfileGraphicController extends EditGraphicController {

    public void navigateToEditView(ProfileView profileView, Band band) {
        profileView.setProfilePage(new EditBandView(band, profileView));
    }

    public void navigateToProfileView(ProfileView profileView, Band band) {
        profileView.setProfilePage(new BandProfileView(profileView, band));
    }
}
