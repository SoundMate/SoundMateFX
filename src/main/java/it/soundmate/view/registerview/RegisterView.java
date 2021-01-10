package it.soundmate.view.registerview;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
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
    private TextField address;
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField email = new TextField();
    private TextField password = new TextField();
    protected UserType userType;

    private final Logger logger = LoggerFactory.getLogger(RegisterView.class);

    public RegisterView(UserType userType) {
        this.mainBorderPane = new BorderPane();
        this.userType = userType;

        UIUtils.setBackgroundImagePane(new Image("soundmate/images/register-band-bg.png"), this.mainBorderPane);
        VBox topVBox = buildTopVBox();
        BorderPane centerBorderPane = buildCenterPane(userType);
        
        this.mainBorderPane.setCenter(centerBorderPane);
        this.mainBorderPane.setTop(topVBox);
    }

    private BorderPane buildCenterPane(UserType userType) {

        switch (userType){
            case SOLO:
                return new RegisterSoloView();
            case BAND:
                return new RegisterBandView();
            case ROOM_RENTER:
                return new RegisterRoomRenterView();
            default:
                logger.error("Error determining type of user");
                //Throw exception
                return null;
        }
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

    private VBox buildTopVBox() {
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
            if (registerBean != null) {
                if (registerBean.checkFields()) {
                    user = registerController.registerUser();
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
            } else {
                logger.info("Error in registration, unable to create Register Bean");
            }
        }

        private RegisterBean determineRegisterBean(UserType userType) {
            switch (userType) {
                case SOLO:
                    return new RegisterSoloBean(email.getText(), password.getText(), firstName.getText(), lastName.getText());
                case BAND:
                    return new RegisterBandBean(email.getText(), password.getText(), bandOrRoomName.getText());
                case ROOM_RENTER:
                    return new RegisterRenterBean(email.getText(), password.getText(), address.getText(), bandOrRoomName.getText());
                default:
                    return null;
            }
        }

    }
}
