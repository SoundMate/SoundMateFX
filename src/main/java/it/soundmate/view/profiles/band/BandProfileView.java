/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 17:56
 * Last edited: 20/01/21, 17:56
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class BandProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(BandProfileView.class);
    private final BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();
    private final ProfileView profileView;
    private final Band band;
    private final List<Application> applicationList;

    //UI
    private final Rectangle coverImg = new Rectangle();
    private HBox genresList = new HBox();

    //Buttons
    private final Button addCoverImgBtn = UIUtils.createStyledButton("Add cover image", new AddImageAction());
    private final Button editProfileBtn = UIUtils.createStyledButton("Edit profile Info", new EditProfileAction());
    private final Button addGenresBtn = UIUtils.createStyledButton("Add Genres", new AddGenreAction());

    public BandProfileView(ProfileView profileView, Band user) {
        this.profileView = profileView;
        this.band = user;
        this.applicationList = bandProfileGraphicController.getApplicationsList(user.getId());
        buildBandProfileView(user);
    }

    private void buildBandProfileView(Band band) {
        StackPane stackPane = this.profileView.buildStackPane(band, addCoverImgBtn, coverImg);
        HBox userInfoVBox = buildUserInfoVBox(band);
        HBox photosHBox = this.profileView.buildMediaHBox(band, new ManageMediaAction());
        HBox membersHBox = buildApplicationHBox();

        Label nameLabel = new Label(band.getBandName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameLabel.setPadding(new Insets(0, 0,0, 25));

        this.getChildren().addAll(stackPane, nameLabel, userInfoVBox, membersHBox, photosHBox);
    }

    private HBox buildApplicationHBox() {
        HBox applicationHBox = new HBox();
        applicationHBox.setPadding(new Insets(0, 25, 0, 25));
        VBox applicationVBox = new VBox();
        Label applicationLabel = new Label("Application");
        applicationLabel.setStyle(Style.MID_LABEL);
        applicationVBox.getChildren().add(applicationLabel);
        applicationHBox.setSpacing(10);
        for (int i = 0; i < applicationList.size(); i++) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            Label title = new Label("Application #"+(i+1));
            title.setStyle(Style.MID_LABEL);
            Button applicationBtn = UIUtils.createStyledButton("Manage", new ApplicationAction(applicationList.get(i)));
            vBox.getChildren().addAll(title, applicationBtn);
            applicationHBox.getChildren().add(vBox);
        }
        UIUtils.addRegion(null, applicationHBox);
        Button newAppBtn = UIUtils.createStyledButton("New Application", new NewApplicationAction());
        applicationHBox.getChildren().add(newAppBtn);
        return applicationHBox;
    }

    private HBox buildUserInfoVBox(Band band) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);

        HBox[] hBoxes = this.profileView.buildProfileSection("Genres", "Add genres", editProfileBtn);
        HBox mainHBox = hBoxes[0];
        this.genresList = hBoxes[1];
        this.genresList.getChildren().remove(0);
        this.genresList.getChildren().add(addGenresBtn);
        for (Genre genre : band.getGenres()) {
            Label genreLabel = new Label(genre.name());
            genreLabel.setStyle(Style.FAV_GENRE_LABEL);
            genreLabel.setPadding(new Insets(5));
            this.genresList.getChildren().add(genreLabel);
        }
        return mainHBox;
    }


    private class AddImageAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add image clicked");
            bandProfileGraphicController.navigateToEditView(profileView, band);
        }
    }

    private static class ManageMediaAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Manage media click");
        }
    }

    private class AddGenreAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add genre click");
            try {
                Genre genre = bandProfileGraphicController.addGenre(band);
                updateGenreUI(genre);
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }

        private void updateGenreUI(Genre genre) {
            Label genreLabel = new Label(genre.name());
            genreLabel.setStyle(Style.FAV_GENRE_LABEL);
            genresList.getChildren().add(genreLabel);
        }
    }

    private class EditProfileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit profile info clicked");
            bandProfileGraphicController.navigateToEditView(profileView, band);
        }
    }

    private class ApplicationAction implements EventHandler<ActionEvent> {
        private final Application application;

        public ApplicationAction(Application application) {
            this.application = application;
        }

        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new ManageApplicationView(profileView, band, application));
        }
    }

    private class NewApplicationAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            profileView.setProfilePage(new NewApplicationView(profileView, band));
        }
    }
}
