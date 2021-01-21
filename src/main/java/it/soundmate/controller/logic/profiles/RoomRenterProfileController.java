/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 18/01/21, 18:10
 * Last edited: 18/01/21, 18:10
 */

package it.soundmate.controller.logic.profiles;


import it.soundmate.bean.AddRoomBean;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;


public class RoomRenterProfileController extends EditController {

    private final RoomRenterDao roomRenterDao = new RoomRenterDao(new UserDao());

    public int addRoom(AddRoomBean addRoomBean, RoomRenter roomRenter) {
        try {
            return roomRenterDao.addRoom(addRoomBean, roomRenter);
        } catch (UpdateException updateException) {
            throw new UpdateException(updateException.getMessage());
        }
    }
}
