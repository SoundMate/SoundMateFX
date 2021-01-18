/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;

import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Solo;
import it.soundmate.utils.ImgBase64Repo;

import java.io.File;
import java.io.IOException;

public class EditProfileSoloController {

    private final UserDao userDao = new UserDao();

    public void updateProfilePic(Solo solo, File image) throws IOException {
        if (userDao.updateProfilePic(solo.getId(), image.toPath()) != 1) {
            throw new UpdateException("Error updating profile pic");
        }
        solo.setEncodedImg(ImgBase64Repo.encode(image.toPath()));
    }
}
