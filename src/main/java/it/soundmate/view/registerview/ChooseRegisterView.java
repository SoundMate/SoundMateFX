package it.soundmate.view.registerview;

import it.soundmate.constants.Style;
import it.soundmate.model.UserType;
import it.soundmate.view.LoginView;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChooseRegisterView extends Pane {

    private final BorderPane borderPane;
    private final VBox vBox = new VBox();
    private final HBox iconsHBox = new HBox();
    private final HBox chooseHBox = new HBox();

    private Logger logger = LoggerFactory.getLogger(ChooseRegisterView.class);

    public ChooseRegisterView() {
        this.borderPane = new BorderPane();
        Image background = new Image("soundmate/images/bg.png");
        UIUtils.setBackgroundImagePane(background, this.borderPane);
        //Top
        buildIconsHBox(iconsHBox);
        buildHeaderVBox(vBox);
        //Center
        buildCenter(chooseHBox);

        this.borderPane.setTop(vBox);
        this.borderPane.setCenter(chooseHBox);
    }

    private void buildCenter(HBox chooseHBox) {
        chooseHBox.setAlignment(Pos.CENTER);
        chooseHBox.setSpacing(100);
        Button registerBandBtn = new Button("Band");
        Button registerRoomBtn = new Button("Band Room");
        Button registerSoloBtn = new Button("Solo");
        registerBandBtn.setStyle(Style.REGISTER_BTN);
        registerRoomBtn.setStyle(Style.REGISTER_BTN);
        registerSoloBtn.setStyle(Style.REGISTER_BTN);
        registerBandBtn.setOnAction(new RegisterBandAction());
        registerRoomBtn.setOnAction(new RegisterBandRoomAction());
        registerSoloBtn.setOnAction(new RegisterSoloAction());

        chooseHBox.getChildren().addAll(registerBandBtn, registerRoomBtn, registerSoloBtn);
    }

    private void buildHeaderVBox(VBox vBox) {
        Image logo = new Image("soundmate/images/logo-vertical.png");
        ImageView logoImgView = new ImageView(logo);
        logoImgView.setFitWidth(200);
        logoImgView.setFitHeight(150);
        logoImgView.setPreserveRatio(true);
        vBox.getChildren().add(this.iconsHBox);
        vBox.getChildren().add(logoImgView);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(50));
    }

    private void buildIconsHBox(HBox iconsHBox) {
        Image backIcon = new Image("soundmate/icons/left-arrow-white.png");
        Image infoIcon = new Image("soundmate/icons/050-info.png");
        ImageView back = new ImageView(backIcon);
        ImageView info = new ImageView(infoIcon);
        back.setFitWidth(24);
        back.setFitHeight(24);
        info.setFitHeight(24);
        info.setFitWidth(24);

        back.setOnMouseClicked(new BackAction());
        info.setOnMouseClicked(new InfoAction());

        iconsHBox.getChildren().add(back);
        UIUtils.addRegion(null, iconsHBox);
        iconsHBox.getChildren().add(info);
    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    private class RegisterBandAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Band Click");
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent registerView = new RegisterView(UserType.BAND).getMainBorderPane();
            Scene scene = new Scene(registerView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class RegisterBandRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Band Room Click");
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent registerView = new RegisterView(UserType.ROOM_RENTER).getMainBorderPane();
            Scene scene = new Scene(registerView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class RegisterSoloAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Solo Click");
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Parent registerView = new RegisterView(UserType.SOLO).getMainBorderPane();
            Scene scene = new Scene(registerView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class BackAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            logger.info("Back Pressed");
            Stage stage = (Stage) vBox.getScene().getWindow();
            Parent loginView = new LoginView();
            Scene scene = new Scene(loginView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }

    private class InfoAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            logger.info("Info Clicked");
        }
    }
}
