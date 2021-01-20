package it.soundmate.view.main;
import it.soundmate.constants.Style;
import it.soundmate.model.Band;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.profiles.band.BandProfileView;
import it.soundmate.view.profiles.renter.RenterProfileView;
import it.soundmate.view.profiles.solo.SoloProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.List;

public class ProfileView extends Pane {

    private final VBox profileVBox;
    private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);

    public ProfileView(User user){
        this.profileVBox = new VBox();
        UIUtils.setBackgroundPane("#232323", this.profileVBox);
        switch (user.getUserType()) {
            case SOLO:
                this.profileVBox.getChildren().add(new SoloProfileView((Solo) user, this));
                break;
            case ROOM_RENTER:
                this.profileVBox.getChildren().add(new RenterProfileView(this, (RoomRenter) user));
                break;
            case BAND:
                this.profileVBox.getChildren().add(new BandProfileView(this, (Band) user));
                break;
            default:
                logger.error("Error in profile view constructor");
        }
    }


    public VBox getProfileVBox(){
        return this.profileVBox;
    }

    public void setProfilePage(Pane profilePage) {
        this.profileVBox.getChildren().set(0, profilePage);
        logger.info("Profile Page Set");
    }

    public HBox buildMediaHBox(User user, EventHandler<ActionEvent> handler) {
        HBox mediaSection = new HBox();
        mediaSection.setPadding(new Insets(25));
        mediaSection.setAlignment(Pos.BOTTOM_CENTER);

        VBox titleAndList = new VBox();
        titleAndList.setAlignment(Pos.BOTTOM_LEFT);
        titleAndList.setSpacing(10);

        Label mediaTitleLabel = new Label("Photos");
        mediaTitleLabel.setStyle(Style.MID_LABEL);
        titleAndList.getChildren().add(mediaTitleLabel);

        if (user.getPhotos() != null && !user.getPhotos().isEmpty()) {
            //Display Photo List (Add children to this.photoList)
        } else {
            Label defaultString = new Label("Add photos in the manage media section");
            defaultString.setStyle(Style.LOW_LABEL);
            titleAndList.getChildren().add(defaultString);
        }

        Button manageMediaBtn = UIUtils.createStyledButton("Manage Media", handler);
        mediaSection.getChildren().add(titleAndList);
        UIUtils.addRegion(null, mediaSection);
        mediaSection.getChildren().add(manageMediaBtn);
        return mediaSection;
    }

    public StackPane buildStackPane(User user, Button addCoverImgBtn, Rectangle coverImg) {
        StackPane tempStackPane = new StackPane();
        tempStackPane.setPrefHeight(175);
        tempStackPane.setPrefWidth(USE_COMPUTED_SIZE);
        tempStackPane.setAlignment(Pos.CENTER);

        //Profile Picture
        coverImg.setHeight(175);
        coverImg.setWidth(600);
        if (user.getEncodedImg() != null) {
            coverImg.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(user.getId()))));
            tempStackPane.getChildren().add(coverImg);
        } else {
            tempStackPane.getChildren().add(coverImg);
            tempStackPane.getChildren().add(addCoverImgBtn);
        }
        return tempStackPane;
    }

    public HBox[] buildProfileSection(String title, String emptyLabel, Button endingButton) {
        HBox mainHBox = new HBox();
        mainHBox.setPrefWidth(USE_COMPUTED_SIZE);
        mainHBox.setPrefHeight(USE_COMPUTED_SIZE);
        mainHBox.setSpacing(10);
        mainHBox.setAlignment(Pos.CENTER_LEFT);
        mainHBox.setPadding(new Insets(0, 25, 0, 25));

        VBox titleAndContent = new VBox();
        titleAndContent.setPrefHeight(USE_COMPUTED_SIZE);
        titleAndContent.setPrefWidth(USE_COMPUTED_SIZE);
        titleAndContent.setSpacing(10);

        HBox contentHBox = new HBox();
        contentHBox.setPrefWidth(USE_COMPUTED_SIZE);
        contentHBox.setPrefHeight(USE_COMPUTED_SIZE);
        contentHBox.setSpacing(10);
        contentHBox.setAlignment(Pos.CENTER_LEFT);

        Label empty = new Label(emptyLabel);
        empty.setStyle(Style.LOW_LABEL);
        contentHBox.getChildren().add(empty);

        Label titleLabel = new Label(title);
        titleLabel.setStyle(Style.MID_LABEL);
        titleAndContent.getChildren().addAll(titleLabel, contentHBox);

        mainHBox.getChildren().add(titleAndContent);
        UIUtils.addRegion(null, mainHBox);
        mainHBox.getChildren().add(endingButton);
        HBox[] resultHBox = new HBox[2];
        resultHBox[0] = mainHBox;
        resultHBox[1] = contentHBox;
        return resultHBox;
    }

}
