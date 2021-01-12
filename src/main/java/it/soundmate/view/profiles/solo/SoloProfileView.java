package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.controller.SoloProfileSoloController;
import it.soundmate.database.dao.UserDao;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

public class SoloProfileView extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(SoloProfileView.class);
    private final Solo soloUser;
    private final SoloProfileSoloController soloProfileSoloController = new SoloProfileSoloController();
    private final ProfileView profileView;

    //UI
    private HBox favGenresHBoxList;
    private Button addGenreBtn;

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
        HBox mediaSection = new HBox();
        mediaSection.setPadding(new Insets(25));
        mediaSection.setAlignment(Pos.BOTTOM_CENTER);

        VBox titleAndList = new VBox();
        titleAndList.setAlignment(Pos.BOTTOM_LEFT);
        titleAndList.setSpacing(10);

        Label mediaTitleLabel = new Label("Photos");
        mediaTitleLabel.setStyle(Style.MID_LABEL);
        titleAndList.getChildren().add(mediaTitleLabel);

        if (this.soloUser.getPhotos() != null && !this.soloUser.getPhotos().isEmpty()) {
            //Display Photo List (Add children to this.photoList)
        } else {
            Label defaultString = new Label("Add photos in the manage media section");
            defaultString.setStyle(Style.LOW_LABEL);
            titleAndList.getChildren().add(defaultString);
        }

        Button manageMediaBtn = UIUtils.createStyledButton("Manage Media", new ManageMediaAction());
        mediaSection.getChildren().add(titleAndList);
        UIUtils.addRegion(null, mediaSection);
        mediaSection.getChildren().add(manageMediaBtn);
        return mediaSection;
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

        //private HBox bandsList;
        //private HBox mediaList;
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
            for (String favGenre : this.soloUser.getFavGenres()) {
                Label genreLabel = new Label(favGenre);
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

        HBox instrumentList = new HBox();
        instrumentList.setAlignment(Pos.CENTER);
        //Get solo instrument list and for every instrument add a circle with the corresponding icon
        //and label
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

        instrumentSection.setPrefWidth(USE_COMPUTED_SIZE);
        instrumentSection.setPrefHeight(200);
        instrumentSection.getChildren().addAll(instrumentLabelHeader, instrumentList);
        return instrumentSection;
    }


    private static class AddInstrumentAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add instrument Clicked");
        }
    }

    private class AddGenreAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add Genre Clicked");
            Optional<String> result = favGenreDialog();
            result.ifPresent(s -> logger.info("User input genre: {}", s));
            if (result.isPresent() && soloProfileSoloController.addGenre(result.get(), soloUser)) {
                logger.info("Added new genre to solo user: {} {}; {}",soloUser.getFirstName(), soloUser.getLastName(), result.get());
                updateUI(result.get());
            } else {
                logger.info("Error adding genre");
            }
        }

        private Optional<String> favGenreDialog() {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Favourite Genres");
            dialog.setHeaderText("Add a new favourite genre");
            dialog.setContentText("Enter a genre:");
            return dialog.showAndWait();
        }

        private void updateUI(String newGenre) {
            Label newGenreLabel = new Label(newGenre);
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
            UserDao userDao = new UserDao();
            profileView.setProfilePage(new EditProfileSolo(soloUser, profileView, userDao));
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
