package it.soundmate.view.registerview;

import it.soundmate.model.UserType;
import it.soundmate.view.UIUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterView extends Pane {

    private final BorderPane mainBorderPane;
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
                logger.info("Building Solo Register View");
                return new RegisterSoloView();
            case BAND:
                logger.info("Building Band Register View");
                return new RegisterBandView();
            case ROOM_RENTER:
                logger.info("Building Room Renter Register View");
                return new RegisterRoomRenterView();
            default:
                logger.error("Error determining type of user");
                //Throw exception
                return null;
        }
    }

    public static HBox createEmailAndPasswordHBox(VBox emailVBox, VBox passwordVBox) {
        HBox emailAndPassword = new HBox();
        emailAndPassword.setAlignment(Pos.CENTER);
        emailAndPassword.setSpacing(10);
        emailAndPassword.setPrefWidth(USE_COMPUTED_SIZE);
        emailAndPassword.setPrefHeight(USE_COMPUTED_SIZE);
        emailAndPassword.getChildren().addAll(emailVBox, passwordVBox);
        return emailAndPassword;
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

    public static VBox styleVBoxFields() {
        VBox fieldsVBox = new VBox();
        fieldsVBox.setSpacing(20);
        fieldsVBox.setAlignment(Pos.CENTER);
        fieldsVBox.setPrefWidth(USE_COMPUTED_SIZE);
        fieldsVBox.setPrefHeight(USE_COMPUTED_SIZE);
        return fieldsVBox;
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
}
