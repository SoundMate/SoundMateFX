/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 00:24
 * Last edited: 25/01/21, 00:24
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;

public class BandProfileController {

    private final UserDao userDao = new UserDao();
    private final BandDao bandDao = new BandDao(userDao);

    public Genre addGenre(Genre genre, Band band) {
        if (bandDao.updateGenre(band, genre)) {
            return genre;
        } else {
            throw new InputException("Genre not added (Controller Insert)");
        }
    }

}
