package it.soundmate.view.registerview;

import it.soundmate.constants.Style;
import it.soundmate.controller.graphic.ChooseRegisterController;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private final ChooseRegisterController chooseRegisterController = new ChooseRegisterController();

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
        Button registerRoomBtn = new Button("Room Renter");
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
        ImageView back = new ImageView(backIcon);
        back.setFitWidth(24);
        back.setFitHeight(24);

        back.setOnMouseClicked(new BackAction());

        iconsHBox.getChildren().add(back);
        UIUtils.addRegion(null, iconsHBox);
    }


    public BorderPane getBorderPane() {
        return borderPane;
    }

    private class RegisterBandAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Band Click");
            chooseRegisterController.navigateToRegisterBandView((Stage) borderPane.getScene().getWindow());
        }
    }

    private class RegisterBandRoomAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Band Room Click");
            chooseRegisterController.navigateToRegisterRoomRenterView((Stage) borderPane.getScene().getWindow());
        }
    }

    private class RegisterSoloAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Register Solo Click");
            chooseRegisterController.navigateToRegisterSoloView((Stage) borderPane.getScene().getWindow());
        }
    }

    private class BackAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            logger.info("Back Pressed");
            chooseRegisterController.backToLoginView((Stage) borderPane.getScene().getWindow());
        }
    }
}
