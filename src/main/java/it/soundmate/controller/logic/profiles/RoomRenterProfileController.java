/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:10
 * Last edited: 18/01/21, 18:10
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import it.soundmate.utils.ImgBase64Repo;

import java.io.File;
import java.io.IOException;

public class RoomRenterProfileController {

    private final UserDao userDao = new UserDao();

    public void addCoverImage(File image, RoomRenter roomRenter) throws IOException {
        if (userDao.updateProfilePic(roomRenter.getId(), image.toPath()) != 1) {
            throw new UpdateException("Error uploading profile image");
        } else {
            roomRenter.setEncodedImg(ImgBase64Repo.encode(image.toPath()));
        }
    }

}
