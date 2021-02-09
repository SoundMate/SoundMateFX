/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 27/01/21, 18:18
 * Last edited: 27/01/21, 18:18
 */

package it.soundmate.view.profiles.band;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.BandProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Band;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.EditProfileView;
import it.soundmate.model.SocialLinks;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

public class UpdateSocialView extends EditProfileView {

    private static final Logger logger = LoggerFactory.getLogger(UpdateSocialView.class);
    public static final String UPDATE1 = "Update";
    private final Band band;
    private final ProfileView profileView;
    private final BandProfileGraphicController bandProfileGraphicController = new BandProfileGraphicController();

    //UI
    private final TextField spotifyTextField = new TextField();
    private final TextField youtubeTextField = new TextField();
    private final TextField facebookTextField = new TextField();

    //Buttons
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
    private final Button spotifyBtn = UIUtils.createStyledButton(UPDATE1, new UpdateSocialAction(SocialLinks.SPOTIFY));
    private final Button youtubeBtn = UIUtils.createStyledButton(UPDATE1, new UpdateSocialAction(SocialLinks.YOUTUBE));
    private final Button facebookBtn = UIUtils.createStyledButton(UPDATE1, new UpdateSocialAction(SocialLinks.FACEBOOK));

    public UpdateSocialView(Band band, ProfileView profileView) {
        this.band = band;
        this.profileView = profileView;
        this.buildVBox();
    }

    private void buildVBox() {
        this.setPadding(new Insets(25));
        this.setSpacing(20);
        Label label = new Label("Social Links for "+band.getBandName());
        label.setStyle(Style.HEADER_TEXT);

        this.getChildren().add(label);

        HBox spotifyHBox = buildEditRow("Spotify: ", "", spotifyTextField, spotifyBtn);
        HBox youtubeHBox = buildEditRow("YouTube: ", "", youtubeTextField, youtubeBtn);
        HBox facebookHBox = buildEditRow("Facebook: ", "", facebookTextField, facebookBtn);

        this.getChildren().addAll(spotifyHBox, youtubeHBox, facebookHBox);
        this.getChildren().add(this.backBtn);
    }


    private class UpdateSocialAction implements EventHandler<ActionEvent> {

        private final SocialLinks socialLinks;

        public UpdateSocialAction(SocialLinks socialLinks) {
            this.socialLinks = socialLinks;
        }

        @Override
        public void handle(ActionEvent event) {
            try {
                switch (socialLinks) {
                    case SPOTIFY:
                        socialLinks.setLink(Objects.requireNonNullElse(spotifyTextField.getText(), "Null"));
                        break;
                    case YOUTUBE:
                        socialLinks.setLink(Objects.requireNonNullElse(youtubeTextField.getText(), "Null"));
                        break;
                    case FACEBOOK:
                        socialLinks.setLink(Objects.requireNonNullElse(facebookTextField.getText(), "Null"));
                        break;
                }
                bandProfileGraphicController.addSocialLink(socialLinks, band);
            } catch (InputException inputException) {
                logger.error(inputException.getMessage());
            }
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            bandProfileGraphicController.navigateToProfileView(profileView, band);
        }
    }

}
