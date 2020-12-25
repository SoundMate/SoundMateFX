package it.soundmate.controller;

import it.soundmate.database.SoloDao;
import it.soundmate.model.BandManager;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;

public class ProfileController {

    private final SoloDao soloDao = SoloDao.getInstance();

    public Solo getSoloFromUser(User user) {
        return soloDao.getSoloByUser(user);
    }

    /*
    public BandManager getBandManagerFromUser(User user){
        return null;
    }

    public RoomRenter getRoomRenterFromUser(User user){
        return null;
    }
    */
}
