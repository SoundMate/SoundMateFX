package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.controller.logic.EditProfileSoloController;
import it.soundmate.database.dao.UserDao;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Solo;
import it.soundmate.utils.Cache;
import it.soundmate.utils.ImagePicker;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class EditProfileSolo extends VBox {

    private static final Logger logger = LoggerFactory.getLogger(EditProfileSolo.class);
    private final ProfileView profileView;
    private final EditProfileSoloController editProfileSoloController;
    private final Solo solo;
    private final TextField updateEmailTextField = new TextField();
    private final TextField updatePassTextField = new TextField();
    private final TextField updateFirstNameTextField = new TextField();
    private final TextField updateLastNameTextField = new TextField();
    private Circle profilePicImg;
    private final UserDao userDao;

    public EditProfileSolo(Solo solo, ProfileView profileView, UserDao userDao){
        this.solo = solo;
        this.profileView = profileView;
        this.setPadding(new Insets(25));
        this.setAlignment(Pos.CENTER);
        this.userDao = userDao;
        this.editProfileSoloController = new EditProfileSoloController(userDao);


        HBox profilePicSection = buildProfilePicSection();
        Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
        backBtn.setPadding(new Insets(0, 25, 0, 25));
        UIUtils.addSizedRegion(this, 25, 25);
        this.getChildren().add(profilePicSection);
        VBox updateInfoVBox = buildUpdateInfoVBox();
        this.getChildren().add(updateInfoVBox);
        this.getChildren().add(backBtn);
    }

    private VBox buildUpdateInfoVBox() {
        VBox updateInfoVBox = new VBox();
        updateInfoVBox.setPadding(new Insets(25));
        updateInfoVBox.setAlignment(Pos.CENTER_LEFT);
        updateInfoVBox.setSpacing(20);

        HBox updateEmailHBox = buildSingleUpdateInfoHBox("Email", this.solo.getEmail(), updateEmailTextField);
        HBox updatePasswordHBox = buildSingleUpdateInfoHBox("Password", "********", updatePassTextField);
        HBox updateFirstNameHBox = buildSingleUpdateInfoHBox("First Name", this.solo.getFirstName(), updateFirstNameTextField);
        HBox updateLastNameHBox = buildSingleUpdateInfoHBox("Last Name", this.solo.getLastName(), updateLastNameTextField);

        updateInfoVBox.getChildren().addAll(updateEmailHBox, updatePasswordHBox, updateFirstNameHBox, updateLastNameHBox);
        return updateInfoVBox;
    }

    private HBox buildSingleUpdateInfoHBox(String label, String content, TextField textField) {
        HBox updateSingleHBox = new HBox();
        updateSingleHBox.setAlignment(Pos.CENTER_LEFT);
        updateSingleHBox.setSpacing(10);

        Label updateLabel = new Label(label+": ");
        updateLabel.setStyle(Style.MID_LABEL);
        Label contentLabel = new Label(content);
        contentLabel.setStyle(Style.MID_LABEL);

        updateSingleHBox.getChildren().addAll(updateLabel, contentLabel);
        UIUtils.addRegion(null, updateSingleHBox);
        textField.setStyle(Style.TEXT_FIELD);
        Button button = UIUtils.createStyledButton("Update", new UpdateInfoAction(label));
        updateSingleHBox.getChildren().addAll(textField, button);
        return updateSingleHBox;
    }

    private HBox buildProfilePicSection() {
        HBox profilePicSection = new HBox();
        profilePicSection.setPadding(new Insets(25));
        profilePicSection.setAlignment(Pos.CENTER);

        //Profile Pic
        this.profilePicImg = new Circle();
        profilePicImg.setRadius(65);
        if (this.solo.getEncodedImg() != null) {
            Image image = new Image(Cache.getInstance().getProfilePicFromCache(this.solo.getId()));
            profilePicImg.setFill(new ImagePattern(image));
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
        Button updateProfilePicBtn = UIUtils.createStyledButton("Update Profile Pic", new UpdateProfilePicAction());
        Button removeProfilePicBtn = UIUtils.createStyledButton("Remove Profile Pic", new RemoveProfilePicAction());
        updateProfilePicButtons.getChildren().addAll(updateProfilePicBtn, removeProfilePicBtn);

        UIUtils.addRegion(null, profilePicSection);
        profilePicSection.getChildren().add(updateProfilePicButtons);
        return profilePicSection;
    }


    private class UpdateProfilePicAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Update profile pic clicked");
            ImagePicker imagePicker = new ImagePicker();
            File chosenImage = imagePicker.chooseImage(profilePicImg);
            if (chosenImage != null && Cache.getInstance().saveProfilePicToCache(solo, chosenImage)) {
                //Saved to Cache
                //Save to DB
                try {
                    editProfileSoloController.updateProfilePic(solo, chosenImage);
                } catch (UpdateException ue) {
                    logger.info("Error updating profile picture (UpdateException)");
                } catch (IOException ioe) {
                    logger.info("Error updating profile picture in model (IOException)");
                }
                profilePicImg.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(solo.getId()))));
            }
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
            profileView.setProfilePage(new SoloProfileView(solo, profileView));
        }
    }

    private class UpdateInfoAction implements EventHandler<ActionEvent> {

        private final String updateType;

        public UpdateInfoAction(String updateType){
            this.updateType = updateType;
        }

        @Override
        public void handle(ActionEvent event) {
            switch (updateType) {
                case "Email":
                    logger.info("Update Email Clicked: {}", updateEmailTextField.getText());
                    break;
                case "Password":
                    logger.info("Update Password Clicked: {}", updatePassTextField.getText());
                    break;
                case "First Name":
                    logger.info("Update First Name Clicked: {}", updateFirstNameTextField.getText());
                    break;
                case "Last Name":
                    logger.info("Update Last Name Clicked: {}", updateLastNameTextField.getText());
                    break;
                default:
                    logger.info("Error: Button Undetected");
            }
        }
    }
}
