/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 12/01/21, 15:22
 * Last edited: 11/01/21, 20:53
 */

package it.soundmate.controller.logic;

import it.soundmate.controller.logic.profiles.EditController;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;

import java.util.ArrayList;
import java.util.List;

public class SoloProfileController extends EditController {

    private final UserDao userDao = new UserDao();

    public List<String> getSoloInstruments(Solo solo) {
        return solo.getInstruments();
    }

    public Genre addGenre(Genre genre, Solo solo) {
        SoloDao soloDao = new SoloDao(userDao);
        if (solo.getFavGenres().isEmpty()) {
            List<Genre> genreList = new ArrayList<>();
            genreList.add(genre);
            if (soloDao.insertGenres(solo, genreList)) {
                return genre;
            } else {
                throw new InputException("Genre not added (Controller Insert)");
            }
        } else {
            if (soloDao.updateGenre(solo, genre)) {
                return genre;
            } else {
                throw new InputException("Genre not added (Controller Update)");
            }
        }
    }
}
