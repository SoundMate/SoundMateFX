/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 12:36
 * Last edited: 08/01/21, 12:36
 */

package it.soundmate.controller;

import it.soundmate.database.DBServices;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Solo;
import it.soundmate.utils.ImgBase64Repo;

import java.io.File;
import java.io.IOException;

public class EditProfileSoloController {

    private final DBServices dbServices = DBServices.getInstance();

    public void updateProfilePic(Solo solo, File image) throws IOException {
        if (dbServices.uploadHandler(solo.getUserID(), image.toPath()) != 1) {
            throw new UpdateException("Error updating profile pic");
        }
        solo.setEncodedImg(ImgBase64Repo.encode(image.toPath()));
    }
}
