/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 05/02/21, 12:22
 * Last edited: 05/02/21, 12:22
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.dao.ApplicationDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;

import java.util.List;

public class ApplicationController {

    private final ApplicationDao applicationDao = new ApplicationDao();

    public List<SoloResultBean> getAppliedSoloBeans(Application application) {
        try {
            return applicationDao.getSolosApplied(application);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public List<Application> getApplicationsForBand(int id) {
        try {
            return applicationDao.getApplicationByBandId(id);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
