package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.SoloProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.uicomponents.InstrumentGraphics;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoloProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(SoloProfileView.class);
    private final Solo soloUser;
    private final ProfileView profileView;

    //UI
    private HBox favGenresHBoxList;
    private Button addGenreBtn;
    private final HBox instrumentList = new HBox();

    public SoloProfileView(Solo solo, ProfileView profileView) {
        this.soloUser = solo;
        this.profileView = profileView;
        HBox userInfoHBox = buildUserInfoHBox();
        HBox favGenresHBox = buildFavGenresHBox();
        HBox bandsSection = buildBandsSection();
        HBox mediaSection = buildMediaSection();
        this.getChildren().addAll(userInfoHBox, favGenresHBox, bandsSection, mediaSection);
    }

    private HBox buildMediaSection() {
        return this.profileView.buildMediaHBox(soloUser, new ManageMediaAction());
    }

    private HBox buildBandsSection() {
        HBox bandsSection = new HBox();
        bandsSection.setPadding(new Insets(25));
        bandsSection.setAlignment(Pos.BOTTOM_CENTER);

        VBox titleAndList = new VBox();
        titleAndList.setAlignment(Pos.BOTTOM_LEFT);
        titleAndList.setSpacing(10);

        Label bandTitleLabel = new Label("Bands");
        bandTitleLabel.setStyle(Style.MID_LABEL);
        titleAndList.getChildren().add(bandTitleLabel);

        if (this.soloUser.getBands() != null && !this.soloUser.getBands().isEmpty()) {
            //Display Band List (Add children to this.bandsList)
        } else {
            Label defaultString = new Label("Search for a Band and send request to join");
            defaultString.setStyle(Style.LOW_LABEL);
            titleAndList.getChildren().add(defaultString);
        }

        Button searchBandsBtn = UIUtils.createStyledButton("Search Bands", new SearchBandAction());
        bandsSection.getChildren().add(titleAndList);
        UIUtils.addRegion(null, bandsSection);
        bandsSection.getChildren().add(searchBandsBtn);
        return bandsSection;
    }

    private HBox buildFavGenresHBox() {
        //Main HBox
        HBox favGenresSectionHBox = new HBox();
        favGenresSectionHBox.setAlignment(Pos.BOTTOM_CENTER);
        favGenresSectionHBox.setPadding(new Insets(25));

        VBox favGenresVBox = new VBox(); //Vbox for title and list of genres
        this.favGenresHBoxList = new HBox(); //List of genres

        favGenresVBox.setAlignment(Pos.BOTTOM_LEFT);
        favGenresVBox.setPrefWidth(USE_COMPUTED_SIZE);
        favGenresVBox.setPrefHeight(USE_COMPUTED_SIZE);

        favGenresHBoxList.setAlignment(Pos.CENTER_LEFT);
        favGenresHBoxList.setPrefHeight(USE_COMPUTED_SIZE);
        favGenresHBoxList.setPrefWidth(USE_COMPUTED_SIZE);

        Label favGenreLabel = new Label("Favourite Genres");
        favGenreLabel.setStyle(Style.MID_LABEL);

        if (this.soloUser.getFavGenres() != null && !this.soloUser.getFavGenres().isEmpty()) {
            for (Genre favGenre : this.soloUser.getFavGenres()) {
                Label genreLabel = new Label(favGenre.name());
                genreLabel.setPadding(new Insets(5));
                genreLabel.setStyle(Style.FAV_GENRE_LABEL);
                favGenresHBoxList.getChildren().add(genreLabel);
                UIUtils.addSizedRegion(favGenresHBoxList, 10,10);
            }
        }
        this.addGenreBtn = UIUtils.createStyledButton("Add +", new AddGenreAction());
        favGenresHBoxList.getChildren().add(this.addGenreBtn);

        favGenresVBox.getChildren().addAll(favGenreLabel, favGenresHBoxList); //Vbox for title and list of genres
        favGenresVBox.setSpacing(15);

        favGenresSectionHBox.getChildren().add(favGenresVBox);  //Main hbox
        UIUtils.addRegion(null, favGenresSectionHBox);
        UIUtils.addStyledButton("Edit Profile Info", new EditProfileAction(), favGenresSectionHBox);
        return favGenresSectionHBox;
    }

    private HBox buildUserInfoHBox() {
        HBox userInfoHBox = new HBox();
        userInfoHBox.setAlignment(Pos.CENTER);
        userInfoHBox.setPadding(new Insets(25));

        //Profile Pic
        Circle profilePic = new Circle();
        profilePic.setRadius(65);
        Image profileImg;
        if (this.soloUser.getEncodedImg() == null) {
            profileImg = new Image("soundmate/images/user-default.png");
        } else {
            profileImg = new Image(Cache.getInstance().getProfilePicFromCache(this.soloUser.getId()));
        }
        profilePic.setFill(new ImagePattern(profileImg));

        //Name
        Label nameLabel = new Label(this.soloUser.getFirstName() + " " + this.soloUser.getLastName());
        nameLabel.setStyle(Style.PROFILE_NAME);
        nameLabel.setPadding(new Insets(0, 0, 0, 15));

        //Instrument section
        VBox instrumentSection = buildInstrumentSection();
        instrumentSection.setPadding(new Insets(0, 0, 0, 30));

        userInfoHBox.getChildren().addAll(profilePic, nameLabel);
        //Region
        UIUtils.addRegion(null, userInfoHBox);
        userInfoHBox.getChildren().add(instrumentSection);

        return userInfoHBox;
    }

    private VBox buildInstrumentSection() {
        VBox instrumentSection = new VBox();
        instrumentSection.setAlignment(Pos.CENTER);

        Label instrumentLabelHeader = new Label("Instruments");
        instrumentLabelHeader.setStyle(Style.MID_LABEL);

        instrumentList.setAlignment(Pos.CENTER);
        //Get solo instrument list and for every instrument add a circle with the corresponding icon
        //and label

        //Add new instrument VBox
        VBox addInstrumentVBox = new VBox();
        addInstrumentVBox.setAlignment(Pos.CENTER);
        addInstrumentVBox.setPadding(new Insets(10));

        Circle addInstrumentCircle = new Circle();
        addInstrumentCircle.setRadius(25);
        addInstrumentCircle.setFill(new ImagePattern(new Image("soundmate/icons/add.png")));
        addInstrumentVBox.getChildren().add(addInstrumentCircle);

        UIUtils.addStyledButton("Add", new AddInstrumentAction(),addInstrumentVBox);
        instrumentList.getChildren().add(addInstrumentVBox);

        if (this.soloUser.getInstruments() != null) {
            for (String instrument : this.soloUser.getInstruments()) {
                VBox instrumentVBox = new VBox();
                instrumentVBox.setAlignment(Pos.CENTER);
                instrumentVBox.setPadding(new Insets(10));

                Label instrumentLabel = new Label(instrument);
                instrumentLabel.setStyle(Style.MID_LABEL);
                instrumentLabel.setPadding(new Insets(10, 0, 0, 0));

                ImageView instrumentImageView = new ImageView();
                instrumentImageView.setFitWidth(32);
                instrumentImageView.setFitHeight(32);
                for (InstrumentGraphics instrumentGraphics : InstrumentGraphics.values()) {
                    if (instrument.equals(instrumentGraphics.getName())) {
                        instrumentImageView.setImage(instrumentGraphics.getSource());
                    }
                }
                instrumentVBox.getChildren().addAll(instrumentImageView, instrumentLabel);
                instrumentList.getChildren().add(instrumentVBox);
            }
        }

        instrumentSection.setPrefWidth(USE_COMPUTED_SIZE);
        instrumentSection.setPrefHeight(200);
        instrumentSection.getChildren().addAll(instrumentLabelHeader, instrumentList);
        return instrumentSection;
    }


    private class AddInstrumentAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add instrument Clicked");
            SoloProfileGraphicController soloProfileGraphicController = new SoloProfileGraphicController();
            try {
                InstrumentGraphics instrument = soloProfileGraphicController.addInstrument(soloUser);
                if (instrument != null) {
                    logger.info("Added new instrument {} to {}", instrument.getName(), soloUser.getEmail());
                    updateInstrumentUI(instrument);
                }
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }

        private void updateInstrumentUI(InstrumentGraphics instrument) {
            VBox instrumentVBox = new VBox();
            instrumentVBox.setAlignment(Pos.CENTER);
            instrumentVBox.setPadding(new Insets(10));

            Label instrumentLabel = new Label(instrument.getName());
            instrumentLabel.setStyle(Style.MID_LABEL);
            instrumentLabel.setPadding(new Insets(10, 0, 0, 0));

            ImageView instrumentImageView = new ImageView();
            instrumentImageView.setFitWidth(32);
            instrumentImageView.setFitHeight(32);
            instrumentImageView.setImage(instrument.getSource());

            instrumentVBox.getChildren().addAll(instrumentImageView, instrumentLabel);
            instrumentList.getChildren().add(instrumentVBox);
        }
    }

    private class AddGenreAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add Genre Clicked");
            SoloProfileGraphicController soloProfileGraphicController = new SoloProfileGraphicController();
            try {
                Genre newGenre = soloProfileGraphicController.addGenre(soloUser);
                if (newGenre != null) {
                    updateUI(newGenre);
                }
            } catch (InputException inputException) {
                logger.error("Input Exception: {}", inputException.getMessage());
            }
        }

        private void updateUI(Genre newGenre) {
            Label newGenreLabel = new Label(newGenre.name());
            newGenreLabel.setStyle(Style.FAV_GENRE_LABEL);
            newGenreLabel.setPadding(new Insets(5));
            favGenresHBoxList.getChildren().add(newGenreLabel);
            UIUtils.addSizedRegion(favGenresHBoxList, 10,10);
            addGenreBtn.toFront();
        }

    }

    private class EditProfileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit Profile Info Clicked");
            profileView.setProfilePage(new EditSoloView(soloUser, profileView));
        }
    }

    private class SearchBandAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Search Bands Clicked");
        }
    }

    private class ManageMediaAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Manage Media Clicked");
            profileView.setProfilePage(new ManageMediaSolo(soloUser, profileView));
        }
    }
}
