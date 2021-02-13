/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 21:37
 * Last edited: 20/01/21, 21:37
 */

package it.soundmate.controller.graphic.profiles;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.controller.logic.ApplicationController;
import it.soundmate.controller.logic.profiles.BandProfileController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import it.soundmate.model.JoinRequest;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.band.BandProfileView;
import it.soundmate.view.profiles.band.EditBandView;
import javafx.scene.control.ChoiceDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BandProfileGraphicController extends EditGraphicController {

    private static final Logger logger = LoggerFactory.getLogger(BandProfileGraphicController.class);
    private final BandProfileController bandProfileController = new BandProfileController();

    public void navigateToEditView(ProfileView profileView, Band band) {
        profileView.setProfilePage(new EditBandView(band, profileView));
    }

    public void navigateToProfileView(ProfileView profileView, Band band) {
        profileView.setProfilePage(new BandProfileView(profileView, band));
    }


    public Genre addGenre(Band band) {
        Genre selectedGenre = playedGenreDialog();
        if (selectedGenre == null) {
            throw new InputException("Genre was not selected");
        } else {
            return bandProfileController.addGenre(selectedGenre, band);
        }
    }

    public static Genre playedGenreDialog() {
        List<String> choices = new ArrayList<>();
        for (Genre genre : Genre.values()) {
            choices.add(genre.name());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("GENRE", choices);
        dialog.setTitle("Add genre");
        dialog.setHeaderText("Add genre");
        dialog.setContentText("Choose genre:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            logger.info("Your choice: {}", result.get());
            return Genre.returnGenre(result.get());
        }
        return null;
    }


    public List<Application> getApplicationsList(int id) {
        try {
            return bandProfileController.getApplicationsList(id);
        } catch (RepositoryException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void addApplication(Application application) {
        try {
            ApplicationController applicationController = new ApplicationController();
            applicationController.addApplication(application);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public SoloResultBean getSoloFromJoinRequest(JoinRequest joinRequest) {
        try {
            return bandProfileController.getSoloFromJoinRequest(joinRequest);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }
}
