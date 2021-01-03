package it.soundmate.view.registerview;

import it.soundmate.bean.RegisterBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.RegisterController;
import it.soundmate.model.User;
import it.soundmate.model.UserType;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private TextField bandOrRoomName;
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField email = new TextField();
    private TextField password = new TextField();
    protected UserType userType;

    private final Logger logger = LoggerFactory.getLogger(RegisterView.class);

    public RegisterView(UserType userType) {
        this.mainBorderPane = new BorderPane();
        this.userType = userType;
        VBox topVBox = new VBox();

        UIUtils.setBackgroundImagePane(new Image("soundmate/images/register-band-bg.png"), this.mainBorderPane);
        topVBox = buildTopVBox(userType);
        BorderPane centerBorderPane = buildCenterPane(userType);
        
        this.mainBorderPane.setCenter(centerBorderPane);
        this.mainBorderPane.setTop(topVBox);
    }

    private BorderPane buildCenterPane(UserType userType) {
        BorderPane borderPane = new BorderPane();
        VBox leftVBox = buildLeftVBox(userType);
        VBox rightVBox = buildRightVBox();

        HBox centerHBox = new HBox();
        UIUtils.setBackgroundPane("#232323", centerHBox);
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.getChildren().add(leftVBox);
        UIUtils.addSizedRegion(centerHBox, 50,50);
        centerHBox.getChildren().add(rightVBox);
        centerHBox.setPadding(new Insets(50));
        borderPane.setCenter(centerHBox);
        return borderPane;
    }

    private VBox buildRightVBox() {
        VBox fieldsVBox = buildFields();
        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        UIUtils.addStyledButtonWidth("Register", new RegisterAction(), buttonHBox, 280);
        fieldsVBox.setPrefWidth(USE_COMPUTED_SIZE);
        fieldsVBox.getChildren().add(buttonHBox);
        return fieldsVBox;
    }

    private VBox buildFields() {
        VBox vBoxToReturn = new VBox();

        HBox nameHBox = new HBox();
        //First Name
        Label firstNameLabel = new Label("First Name");
        firstNameLabel.setStyle(Style.TEXT_FIELD_LABEL);
        this.firstName.setStyle(Style.TEXT_FIELD);
        this.firstName.setPrefWidth(USE_COMPUTED_SIZE);
        VBox firstNameVBox = new VBox();
        firstNameVBox.getChildren().addAll(firstNameLabel, this.firstName);
        //Last Name
        Label lastNameLabel = new Label("Last Name");
        lastNameLabel.setStyle(Style.TEXT_FIELD_LABEL);
        this.lastName.setStyle(Style.TEXT_FIELD);
        this.lastName.setPrefWidth(USE_COMPUTED_SIZE);
        VBox lastNameVBox = new VBox();
        lastNameVBox.getChildren().addAll(lastNameLabel, this.lastName);

        nameHBox.getChildren().addAll(firstNameVBox);
        UIUtils.addSizedRegion(nameHBox, 20, 20);
        nameHBox.getChildren().addAll(lastNameVBox);
        vBoxToReturn.getChildren().add(nameHBox);

        HBox credentialHBox = new HBox();
        //Email
        Label emailLabel = new Label("Email");
        emailLabel.setStyle(Style.TEXT_FIELD_LABEL);
        this.email.setStyle(Style.TEXT_FIELD);
        this.email.setPrefWidth(USE_COMPUTED_SIZE);
        VBox emailVBox = new VBox();
        emailVBox.getChildren().addAll(emailLabel, this.email);

        //Password
        Label passLabel = new Label("Password");
        passLabel.setStyle(Style.TEXT_FIELD_LABEL);
        this.password.setStyle(Style.TEXT_FIELD);
        this.password.setPrefWidth(USE_COMPUTED_SIZE);
        VBox passVBox = new VBox();
        passVBox.getChildren().addAll(passLabel, this.password);

        credentialHBox.getChildren().add(emailVBox);
        UIUtils.addSizedRegion(credentialHBox, 20, 20);
        credentialHBox.getChildren().add(passVBox);
        UIUtils.addSizedRegion(vBoxToReturn, 20, 20);
        vBoxToReturn.getChildren().add(credentialHBox);
        UIUtils.addSizedRegion(vBoxToReturn, 30, 30);
        vBoxToReturn.setPadding(new Insets(25));
        return vBoxToReturn;
    }

    private VBox buildLeftVBox(UserType userType) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(25));
        vBox.setSpacing(20);

        if (userType!= UserType.SOLO) {
            bandOrRoomName = new TextField();
            bandOrRoomName.setAlignment(Pos.CENTER);
            Label bandOrRoomLabel;
            if (userType == UserType.BAND_MANAGER) {
                bandOrRoomName.setPromptText("Band Name...");
                bandOrRoomLabel = new Label("Band Name");
            } else {
                bandOrRoomName.setPromptText("Band Room Name...");
                bandOrRoomLabel = new Label("Band Room Name");
            }
            bandOrRoomLabel.setStyle(Style.TEXT_FIELD_LABEL);
            bandOrRoomName.setStyle(Style.TEXT_FIELD_REGISTER);
            vBox.getChildren().addAll(bandOrRoomLabel,bandOrRoomName);
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

    private class RegisterAction  implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Clicked");
            //Register The User
            RegisterBean registerBean = determineRegisterBean(userType);
            RegisterController registerController = new RegisterController(registerBean);
            User user;
            if (registerController.checkFields(userType)) {
                user = registerController.registerUser(userType);
                if (user == null){
                    logger.info("Error in registration");
                    return;
                }
                Stage stage = (Stage) mainBorderPane.getScene().getWindow();
                Parent profileView = new MainView(user).getBorderPane();
                Scene scene = new Scene(profileView, 800, 600);
                stage.setScene(scene);
                stage.show();
            } else {
                logger.info("Some fields are empty");
            }
        }

        private RegisterBean determineRegisterBean(UserType userType) {
            if (userType == UserType.SOLO) {
                return new RegisterBean(firstName.getText(), lastName.getText(), email.getText(), password.getText());
            } else {
                return new RegisterBean(firstName.getText(), lastName.getText(), email.getText(), password.getText(), bandOrRoomName.getText());
            }
        }

    }
}
