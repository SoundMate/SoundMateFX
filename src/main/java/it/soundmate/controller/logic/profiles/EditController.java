/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 19/01/21, 18:18
 * Last edited: 19/01/21, 18:18
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.utils.ImgBase64Repo;

import java.io.File;
import java.io.IOException;

public class EditController {

    private final UserDao userDao = new UserDao();
    private final RoomRenterDao roomRenterDao = new RoomRenterDao();
    private final BandDao bandDao = new BandDao(userDao);
    private final SoloDao soloDao = new SoloDao(userDao);

    public void updateEmail(String email, User user) {
        try {
            userDao.updateEmail(user, email);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updatePassword(String password, User user) {
        try {
            userDao.updatePassword(user, password);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateProfilePic(File profilePic, User user) throws IOException {
        if (userDao.updateProfilePic(user.getId(), profilePic.toPath()) != 1) {
            throw new UpdateException("Error updating profile pic");
        } else user.setEncodedImg(ImgBase64Repo.encode(profilePic.toPath()));
    }

    public void updateNameRoomRenter(String name, RoomRenter roomRenter) {
        try {
            roomRenterDao.updateName(name, roomRenter);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateCity(String city, User user) {
        try {
            userDao.updateCity(city, user);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateAddress(String address, RoomRenter roomRenter) {
        try {
            roomRenterDao.updateAddress(address, roomRenter);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateNameBand(String name, Band band) {
        try {
            bandDao.updateName(name, band);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateFirstName(String firstName, Solo solo) {
        try {
            soloDao.updateFirstName(solo, firstName);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }

    public void updateLastName(String lastName, Solo solo) {
        try {
            soloDao.updateLastName(solo, lastName);
        } catch (UpdateException updateException){
            throw new UpdateException(updateException.getMessage());
        }
    }
}
