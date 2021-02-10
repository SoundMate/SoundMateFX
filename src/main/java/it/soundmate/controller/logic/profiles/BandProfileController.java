/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 00:24
 * Last edited: 25/01/21, 00:24
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.dao.ApplicationDao;
import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.JoinRequestDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import it.soundmate.model.JoinRequest;

import java.util.List;

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

    public List<Application> getApplicationsList(int id) {
        try {
            ApplicationDao applicationDao = new ApplicationDao();
            return applicationDao.getApplicationByBandId(id);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }


    public SoloResultBean getSoloFromJoinRequest(JoinRequest joinRequest) {
        JoinRequestDao joinRequestDao = new JoinRequestDao();
        return joinRequestDao.getSoloFromJoinRequest(joinRequest);
    }


    public BandResultBean getBandNameByID(int idBand) {
        try {
            return bandDao.getBandName(idBand);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
