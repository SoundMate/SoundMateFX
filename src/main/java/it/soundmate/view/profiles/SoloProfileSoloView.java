package it.soundmate.view.profiles;

import it.soundmate.constants.Style;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SoloProfileSoloView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(SoloProfileSoloView.class);
    private final Solo soloUser;

    public VBox getSoloVBox() {
        return soloVBox;
    }

    private final VBox soloVBox;

    public SoloProfileSoloView(Solo solo) {
        this.soloVBox = new VBox();
        this.soloUser = solo;
        HBox userInfoHBox = buildUserInfoHBox();
        HBox favGenresHBox = buildFavGenresHBox();
        this.soloVBox.getChildren().addAll(userInfoHBox, favGenresHBox);
    }

    private HBox buildFavGenresHBox() {
        //Main HBox
        HBox favGenresSectionHBox = new HBox();
        favGenresSectionHBox.setAlignment(Pos.BOTTOM_CENTER);
        favGenresSectionHBox.setPadding(new Insets(25));

        VBox favGenresVBox = new VBox(); //Vbox for title and list of genres
        HBox favGenresHBox = new HBox(); //List of genres

        favGenresVBox.setAlignment(Pos.BOTTOM_LEFT);
        favGenresVBox.setPrefWidth(USE_COMPUTED_SIZE);
        favGenresVBox.setPrefHeight(USE_COMPUTED_SIZE);

        favGenresHBox.setAlignment(Pos.CENTER_LEFT);
        favGenresHBox.setPrefHeight(USE_COMPUTED_SIZE);
        favGenresHBox.setPrefWidth(USE_COMPUTED_SIZE);

        Label favGenreLabel = new Label("Favourite Genres");
        favGenreLabel.setStyle(Style.MID_LABEL);

        if (this.soloUser.getFavouriteGenres() != null && !this.soloUser.getFavouriteGenres().isEmpty()) {
            for (String favGenre : this.soloUser.getFavouriteGenres()) {
                Label genreLabel = new Label(favGenre);
                genreLabel.setPadding(new Insets(5));
                genreLabel.setStyle(Style.FAVGENRE_LABEL);
                favGenresHBox.getChildren().add(genreLabel);
                UIUtils.addSizedRegion(favGenresHBox, 10,10);
            }
        }
        UIUtils.addStyledButton("Add +", new AddGenreAction(), favGenresHBox);

        favGenresVBox.getChildren().addAll(favGenreLabel, favGenresHBox); //Vbox for title and list of genres
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
        if (this.soloUser.getProfilePic() == null) {
            profileImg = new Image("soundmate/images/user-default.png");
        } else {
            if (Cache.getInstance().getProfilePicFromCache(this.soloUser) != null){
                profileImg = new Image(Cache.getInstance().getProfilePicFromCache(this.soloUser));
            } else {
                profileImg = new Image("soundmate/images/user-default.png");
                logger.info("Unable to load from cache");
            }
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
        if (this.soloUser.getInstrument() != null) {
            for (String instrument : this.soloUser.getInstrument()) {
                VBox instrumentVBox = new VBox();
                instrumentVBox.setAlignment(Pos.CENTER);
                instrumentVBox.setPadding(new Insets(10));

                Label instrumentLabel = new Label(instrument);
                instrumentLabel.setStyle(Style.MID_LABEL);

                Circle instrumentCircle = new Circle();
                instrumentCircle.setRadius(25);

                instrumentVBox.getChildren().addAll(instrumentCircle, instrumentLabel);
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
        }
    }

    private class EditProfileAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Edit Profile Info Clicked");
        }
    }
}
