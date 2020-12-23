package it.soundmate.view.registerview;

import it.soundmate.constants.Style;
import it.soundmate.model.UserType;
import it.soundmate.utils.ImagePicker;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterView extends Pane {

    private final BorderPane mainBorderPane;
    private VBox topVBox;
    private final BorderPane centerBorderPane;
    private ImageView stackProfileImg = new ImageView();
    private Button selectImage = new Button("Add Profile Pic");
    private TextField bandOrRoomName;
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField email = new TextField();
    private TextField password = new TextField();

    private final Logger logger = LoggerFactory.getLogger(RegisterView.class);

    public RegisterView(UserType userType) {
        this.mainBorderPane = new BorderPane();
        this.topVBox = new VBox();

        UIUtils.setBackgroundImagePane(new Image("soundmate/images/register-band-bg.png"), this.mainBorderPane);
        this.topVBox = buildTopVBox(userType);
        this.centerBorderPane = buildCenterPane(userType);
        
        this.mainBorderPane.setCenter(this.centerBorderPane);
        this.mainBorderPane.setTop(this.topVBox);
    }

    private BorderPane buildCenterPane(UserType userType) {
        BorderPane borderPane = new BorderPane();
        VBox leftVBox = buildLeftVBox(userType);
        VBox rightVBox = buildRightVBox();

        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightVBox);
        return borderPane;
    }

    private VBox buildRightVBox() {
        VBox fieldsVBox = buildFields();
        return fieldsVBox;
    }

    private VBox buildFields() {
        VBox vBoxToReturn = new VBox();

        HBox nameHBox = new HBox();
        //First Name
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setStyle(Style.TEXTFIELD_LABEL);
        this.firstName.setStyle(Style.TEXT_FIELD);
        VBox firstNameVBox = new VBox();
        firstNameVBox.getChildren().addAll(firstNameLabel, this.firstName);
        //Last Name
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setStyle(Style.TEXTFIELD_LABEL);
        this.lastName.setStyle(Style.TEXT_FIELD);
        VBox lastNameVBox = new VBox();
        lastNameVBox.getChildren().addAll(lastNameLabel, this.lastName);

        nameHBox.getChildren().addAll(firstNameVBox, lastNameVBox);
        vBoxToReturn.getChildren().add(nameHBox);

        HBox credentialHBox = new HBox();
        //Email
        Label emailLabel = new Label("Email");
        emailLabel.setStyle(Style.TEXTFIELD_LABEL);
        this.email.setStyle(Style.TEXT_FIELD);
        VBox emailVBox = new VBox();
        emailVBox.getChildren().addAll(emailLabel, this.email);

        //Password
        Label passLabel = new Label("Password");
        passLabel.setStyle(Style.TEXTFIELD_LABEL);
        this.password.setStyle(Style.TEXT_FIELD);
        VBox passVBox = new VBox();
        passVBox.getChildren().addAll(passLabel, this.password);

        credentialHBox.getChildren().addAll(emailVBox, passVBox);
        vBoxToReturn.getChildren().add(credentialHBox);
        return vBoxToReturn;
    }

    private VBox buildLeftVBox(UserType userType) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(25));
        vBox.setSpacing(20);

        StackPane imageStackPane = new StackPane();
        stackProfileImg.setFitHeight(175);
        stackProfileImg.setFitWidth(250);
        selectImage.setStyle(Style.BLACK_BTN);
        selectImage.setOnAction(new PictureAction());
        imageStackPane.getChildren().addAll(stackProfileImg, selectImage);

        if (userType!= UserType.SOLO) {
            bandOrRoomName = new TextField();
            bandOrRoomName.setAlignment(Pos.CENTER);
            if (userType == UserType.BAND_MANAGER) {
                bandOrRoomName.setPromptText("Band Name...");
            } else {
                bandOrRoomName.setPromptText("Band Room Name...");
            }
            bandOrRoomName.setStyle(Style.TEXTFIELD_REGISTER);
            vBox.getChildren().addAll(imageStackPane, bandOrRoomName);
        } else {
            vBox.getChildren().add(imageStackPane);
        }
        return vBox;
    }

    private VBox buildTopVBox(UserType userType) {
        VBox top = new VBox();
        //Back img
        Image backImg = new Image("soundmate/icons/left-arrow-white.png");
        ImageView backImgView = new ImageView(backImg);
        backImgView.setOnMouseClicked(new BackAction());
        backImgView.setPreserveRatio(true);
        backImgView.setFitWidth(24);
        top.getChildren().add(backImgView);
        UIUtils.addSizedRegion(top, 50,0);
        //Logo
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Image logoImg = new Image("soundmate/images/logo-h.png");
        ImageView logoImgView = new ImageView(logoImg);
        logoImgView.setPreserveRatio(true);
        logoImgView.setFitWidth(200);
        logoImgView.setFitHeight(150);
        hBox.getChildren().add(logoImgView);
        top.getChildren().add(hBox);
        UIUtils.addSizedRegion(top, 50,0);
        //Label
        Label registerLabel;
        switch (userType) {
            case SOLO:
               registerLabel = new Label("Solo Registration");
               registerLabel.setStyle(Style.HEADER_TEXT);
               top.getChildren().add(registerLabel);
               break;
            case BAND_MANAGER:
                registerLabel = new Label("Band Manager Registration");
                registerLabel.setStyle(Style.HEADER_TEXT);
                top.getChildren().add(registerLabel);
                break;
            case BAND_ROOM_MANAGER:
                registerLabel = new Label("Band Room Registration");
                registerLabel.setStyle(Style.HEADER_TEXT);
                top.getChildren().add(registerLabel);
                break;
            default:
                logger.info("Error in registration instance");
                break;
        }
        top.setPadding(new Insets(50,50,25,50));
        return top;
    }

    public BorderPane getMainBorderPane() {
        return mainBorderPane;
    }

    private class BackAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            logger.info("Back Pressed");
            Stage stage = (Stage) mainBorderPane.getScene().getWindow();
            Parent chooseRegisterView = new ChooseRegisterView().getBorderPane();
            Scene scene = new Scene(chooseRegisterView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class PictureAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            logger.info("Picture Action");
            ImagePicker imagePicker = new ImagePicker();
            imagePicker.chooseImage(stackProfileImg);
            selectImage.setText("Change");
        }
    }
}
