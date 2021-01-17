package it.soundmate.view.main;
import it.soundmate.constants.Style;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.profiles.renter.RenterProfileView;
import it.soundmate.view.profiles.solo.SoloProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

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
            case BAND_MANAGER:
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

}
