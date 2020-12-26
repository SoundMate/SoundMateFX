package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.model.Solo;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EditProfileSolo extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileSolo.class);

    private final VBox editProfileVBox;
    private final Solo solo;
    private Button updateProfilePicBtn;
    private Button removeProfilePicBtn;
    private Button backBtn;

    public EditProfileSolo(Solo solo){
        this.solo = solo;
        this.editProfileVBox = new VBox();
        this.editProfileVBox.setPadding(new Insets(25));
        HBox profilePicSection = buildProfilePicSection();
        this.backBtn = UIUtils.createStyledButton("Back", new BackAction());
        this.editProfileVBox.getChildren().add(this.backBtn);
        UIUtils.addSizedRegion(this.editProfileVBox, 25, 25);
        this.editProfileVBox.getChildren().add(profilePicSection);
    }

    private HBox buildProfilePicSection() {
        HBox profilePicSection = new HBox();
        profilePicSection.setPadding(new Insets(25));
        profilePicSection.setAlignment(Pos.CENTER);

        //Profile Pic
        Circle profilePicImg = new Circle();
        profilePicImg.setRadius(65);
        if (this.solo.getProfilePic() != null && Cache.getInstance().checkProfilePicInCache(solo)) {
            Image profilePic = new Image(Cache.getInstance().getProfilePicFromCache(solo));
            profilePicImg.setFill(new ImagePattern(profilePic));
        } else {
            Image profilePic = new Image("soundmate/images/user-default.png");
            profilePicImg.setFill(new ImagePattern(profilePic));
        }

        //Name Label
        Label nameLabel = new Label(solo.getFirstName() + " " + solo.getLastName());
        nameLabel.setStyle(Style.PROFILE_NAME);

        profilePicSection.getChildren().add(profilePicImg);
        UIUtils.addSizedRegion(profilePicSection, 10, 10);
        profilePicSection.getChildren().add(nameLabel);

        //Buttons for profile pic
        VBox updateProfilePicButtons = new VBox();
        updateProfilePicButtons.setAlignment(Pos.CENTER);
        updateProfilePicButtons.setPrefHeight(USE_COMPUTED_SIZE);
        updateProfilePicButtons.setPrefWidth(USE_COMPUTED_SIZE);
        updateProfilePicButtons.setPadding(new Insets(10));
        updateProfilePicButtons.setSpacing(10);
        this.updateProfilePicBtn = UIUtils.createStyledButton("Update Profile Pic", new UpdateProfilePicAction());
        this.removeProfilePicBtn = UIUtils.createStyledButton("Remove Profile Pic", new RemoveProfilePicAction());
        updateProfilePicButtons.getChildren().addAll(this.updateProfilePicBtn, this.removeProfilePicBtn);

        UIUtils.addRegion(null, profilePicSection);
        profilePicSection.getChildren().add(updateProfilePicButtons);
        return profilePicSection;
    }

    public VBox getEditProfileVBox() {
        return editProfileVBox;
    }

    private class UpdateProfilePicAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Update profile pic clicked");
        }
    }

    private class RemoveProfilePicAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Remove profile pic clicked");
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Back clicked");
            ProfileView.getInstance(solo).setProfilePage(SoloProfileSoloView.getInstance(solo).getSoloVBox());
        }
    }
}
