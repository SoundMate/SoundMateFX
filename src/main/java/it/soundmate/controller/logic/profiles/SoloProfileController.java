/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 00:24
 * Last edited: 24/01/21, 14:06
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;

public class SoloProfileController extends EditController {

    private final UserDao userDao = new UserDao();
    private final SoloDao soloDao = new SoloDao(userDao);

    public Genre addGenre(Genre genre, Solo solo) {
        if (soloDao.updateGenre(solo, genre)) {
            return genre;
        } else {
            throw new InputException("Genre not added (Controller Insert)");
        }
    }

    public String addInstrument(String instrument, Solo solo) {
        if (soloDao.updateInstrument(solo, instrument)) {
            return instrument;
        } else {
            throw new InputException("Instrument not added");
        }
    }
}
