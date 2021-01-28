/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 00:24
 * Last edited: 25/01/21, 00:24
 */

package it.soundmate.controller.logic.profiles;

import it.soundmate.database.dao.BandDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import it.soundmate.view.uicomponents.SocialLinks;

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

    public void addSocialLink(SocialLinks socialLinks, Band band) {
        try {
            switch (socialLinks) {
                case SPOTIFY:
                    bandDao.updateSocialLink(socialLinks.getLink(), 0, band);
                    break;
                case YOUTUBE:
                    bandDao.updateSocialLink(socialLinks.getLink(), 1, band);
                    break;
                case FACEBOOK:
                    bandDao.updateSocialLink(socialLinks.getLink(), 2, band);
                    break;
                default:
                    throw new InputException("Social not recognized");
            }
        } catch (RepositoryException repositoryException) {
            throw new InputException(repositoryException.getMessage());
        }
    }
}
