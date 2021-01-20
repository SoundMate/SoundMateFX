package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.profiles.SoloProfileGraphicController;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Solo;
import it.soundmate.utils.Cache;
import it.soundmate.utils.UserProperties;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.profiles.EditProfileView;
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


public class EditSoloView extends EditProfileView {

    private static final Logger logger = LoggerFactory.getLogger(EditSoloView.class);
    private final ProfileView profileView;
    private final Solo solo;
    private final SoloProfileGraphicController soloProfileGraphicController = new SoloProfileGraphicController();

    //UI
    private final TextField updateEmailTextField = new TextField();
    private final TextField updatePassTextField = new TextField();
    private final TextField updateFirstNameTextField = new TextField();
    private final TextField updateLastNameTextField = new TextField();
    private final TextField updateCityTextField = new TextField();
    private Circle profilePicImg;
    private final Label resultLabel = new Label();

    //Buttons
    private final Button updateEmailBtn = UIUtils.createStyledButton(UPDATE, new EditSoloAction(UserProperties.EMAIL));
    private final Button updatePasswordBtn = UIUtils.createStyledButton(UPDATE, new EditSoloAction(UserProperties.PASSWORD));
    private final Button updateFirstNameBtn = UIUtils.createStyledButton(UPDATE, new EditSoloAction(UserProperties.FIRST_NAME));
    private final Button updateLastNameBtn = UIUtils.createStyledButton(UPDATE, new EditSoloAction(UserProperties.LAST_NAME));
    private final Button updateCityBtn = UIUtils.createStyledButton(UPDATE, new EditSoloAction(UserProperties.CITY));

    public EditSoloView(Solo solo, ProfileView profileView){
        this.solo = solo;
        this.profileView = profileView;
        this.setPadding(new Insets(25));
        this.setAlignment(Pos.CENTER);


        HBox profilePicSection = buildProfilePicSection();
        Button backBtn = UIUtils.createStyledButton("Back", new BackAction());
        backBtn.setPadding(new Insets(0, 25, 0, 25));
        UIUtils.addSizedRegion(this, 25, 25);
        this.getChildren().add(profilePicSection);
        VBox updateInfoVBox = buildUpdateInfoVBox();
        this.getChildren().add(updateInfoVBox);
        this.getChildren().add(backBtn);
        UIUtils.addSizedRegion(this, 50, 50);
        resultLabel.setPadding(new Insets(5));
        this.getChildren().add(resultLabel);
    }

    private VBox buildUpdateInfoVBox() {
        VBox updateInfoVBox = new VBox();
        updateInfoVBox.setPadding(new Insets(25));
        updateInfoVBox.setAlignment(Pos.CENTER_LEFT);
        updateInfoVBox.setSpacing(20);

        HBox updateEmailHBox = buildEditRow("Email: ", this.solo.getEmail(), updateEmailTextField, updateEmailBtn);
        HBox updatePasswordHBox = buildEditRow("Password: ", this.solo.getPassword(), updatePassTextField, updatePasswordBtn);
        HBox updateFirstNameHBox = buildEditRow("First Name: ", this.solo.getFirstName(), updateFirstNameTextField, updateFirstNameBtn);
        HBox updateLastNameHBox = buildEditRow("Last Name: ", this.solo.getLastName(), updateLastNameTextField, updateLastNameBtn);
        HBox updateCityHBox = buildEditRow("City: ", this.solo.getCity(), updateCityTextField, updateCityBtn);

        updateInfoVBox.getChildren().addAll(updateEmailHBox, updatePasswordHBox, updateFirstNameHBox, updateLastNameHBox, updateCityHBox);
        return updateInfoVBox;
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
            try {
                soloProfileGraphicController.updateProfilePic(profilePicImg, solo);
                profilePicImg.setFill(new ImagePattern(new Image(Cache.getInstance().getProfilePicFromCache(solo.getId()))));
            } catch (UpdateException ue) {
                logger.info("Error updating profile picture (UpdateException): {}", ue.getMessage());
            } catch (InputException ie) {
                logger.info("Error updating profile picture in model (InputException): {}", ie.getMessage());
            }
        }
    }

    private static class RemoveProfilePicAction implements EventHandler<ActionEvent> {
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

    private class EditSoloAction implements EventHandler<ActionEvent> {

        private final UserProperties property;

        public EditSoloAction(UserProperties property) {
            this.property = property;
        }

        @Override
        public void handle(ActionEvent event) {
            switch (property) {
                case EMAIL:
                    updateEmail(soloProfileGraphicController, solo, updateEmailTextField, resultLabel);
                    break;
                case PASSWORD:
                    updatePassword(soloProfileGraphicController, solo, updatePassTextField, resultLabel);
                    break;
                case FIRST_NAME:
                    updateFirstName(soloProfileGraphicController, solo, updateFirstNameTextField, resultLabel);
                    break;
                case LAST_NAME:
                    updateLastName(soloProfileGraphicController, solo, updateLastNameTextField, resultLabel);
                    break;
                case CITY:
                    updateCity(soloProfileGraphicController, solo, updateCityTextField, resultLabel);
                    break;
                default:
                    throw new InputException("Non editable field clicked");
            }
        }
    }
}
