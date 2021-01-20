/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 20/01/21, 17:56
 * Last edited: 20/01/21, 17:56
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.model.Band;
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


public class BandProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(BandProfileView.class);
    BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();
    private final ProfileView profileView;
    private final Band band;

    //UI
    private final Rectangle coverImg = new Rectangle();

    //Buttons
    private final Button addCoverImgBtn = UIUtils.createStyledButton("Add cover image", new AddImageAction());
    private final Button searchSolosBtn = UIUtils.createStyledButton("Search Solos", new SearchSoloAction());
    private final Button adSocialBtn = UIUtils.createStyledButton("Add social links", new AddSocialAction());
    private final Button editProfileBtn = UIUtils.createStyledButton("Edit profile Info", new EditProfileAction());
    private final Button addGenresBtn = UIUtils.createStyledButton("Add Genres", new AddGenreAction());

    public BandProfileView(ProfileView profileView, Band user) {
        this.profileView = profileView;
        this.band = user;
        buildBandProfileView(user);
    }

    private void buildBandProfileView(Band band) {
        StackPane stackPane = this.profileView.buildStackPane(band, addCoverImgBtn, coverImg);
        HBox userInfoVBox = buildUserInfoVBox(band);
        HBox photosHBox = this.profileView.buildMediaHBox(band, new ManageMediaAction());
        HBox membersHBox = buildMembersHBox(band);
        HBox socialLinksHBox = buildSocialLinksHBox(band);

        Label nameLabel = new Label(band.getBandName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameLabel.setPadding(new Insets(0, 0,0, 25));

        this.getChildren().addAll(stackPane, nameLabel, userInfoVBox, membersHBox, photosHBox, socialLinksHBox);
    }

    private HBox buildSocialLinksHBox(Band band) {
        HBox[] hBoxes = this.profileView.buildProfileSection("Social Links", "Add social links", adSocialBtn);
        HBox mainHBox = hBoxes[0];
        HBox socialHBox = hBoxes[1];
        if (band.getSocialLinks() != null) {
            //Add social links to socialHBox
        }
        return mainHBox;
    }

    private HBox buildMembersHBox(Band band) {
        HBox[] hBoxes = this.profileView.buildProfileSection("Members", "Send requests to Solos to join your Band", searchSolosBtn);
        HBox mainHBox = hBoxes[0];
        HBox membersHBox = hBoxes[1];
        if (band.getMembers() != null) {
            //Add members to membersHBox
        }
        return mainHBox;
    }

    private HBox buildUserInfoVBox(Band band) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(20);

        HBox[] hBoxes = this.profileView.buildProfileSection("Genres", "Add genres", editProfileBtn);
        HBox mainHBox = hBoxes[0];
        HBox genresHBox = hBoxes[1];
        if (band.getGenres() == null) {
            genresHBox.getChildren().remove(0);
            genresHBox.getChildren().add(addGenresBtn);
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

    private class ManageMediaAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Manage media click");
        }
    }

    private class AddGenreAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add genre click");
        }
    }

    private class EditProfileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit profile info clicked");
            bandProfileGraphicController.navigateToEditView(profileView, band);
        }
    }

    private class SearchSoloAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Search solo click");
        }
    }

    private class AddSocialAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Social action clicked");
        }
    }
}
