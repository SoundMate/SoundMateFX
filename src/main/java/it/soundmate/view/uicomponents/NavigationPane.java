package it.soundmate.view.uicomponents;

import it.soundmate.view.LoginView;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NavigationPane extends Pane {

    /*Singleton*/

    private static NavigationPane instance;
    private final Logger logger = LoggerFactory.getLogger(NavigationPane.class);
    private final List<NavigationItem> navigationItemList = new ArrayList<>();
    private final VBox vBox = new VBox();

    public static NavigationPane getInstance(){
        if (instance == null) {
            instance = new NavigationPane();
        }
        return instance;
    }

    public NavigationPane(){
        this.vBox.setAlignment(Pos.CENTER);
        Image logo = new Image("soundmate/images/logo-onlyimage.png");
        Image background = new Image("soundmate/images/bg.png");
        ImageView logoImgView = new ImageView(logo);
        logoImgView.setFitHeight(70);
        logoImgView.setFitWidth(70);
        logoImgView.setPreserveRatio(true);
        UIUtils.addRegion(this.vBox, null);
        this.vBox.getChildren().add(logoImgView);
        UIUtils.addRegion(this.vBox, null);
        UIUtils.setBackgroundImagePane(background, this.vBox);
        this.initializeNavigation();
        this.vBox.getChildren().addAll(this.navigationItemList);
        UIUtils.addRegion(this.vBox, null);
        UIUtils.addStyledButton("Logout", new LogoutAction(), this.vBox);
        UIUtils.addRegion(this.vBox, null);
        this.vBox.setPrefWidth(200);
        this.vBox.setPrefHeight(USE_COMPUTED_SIZE);
    }

    private void initializeNavigation() {

        NavigationItem home = new NavigationItem("Home", "soundmate/icons/home.png", false, NavigationType.HOME);
        NavigationItem search = new NavigationItem("Search", "soundmate/icons/search.png", false, NavigationType.SEARCH);
        NavigationItem messages = new NavigationItem("Messages", "soundmate/icons/message.png", false, NavigationType.MESSAGES);
        NavigationItem profile = new NavigationItem("Profile", "soundmate/icons/profile.png", true, NavigationType.PROFILE);
        NavigationItem settings = new NavigationItem("Settings", "soundmate/icons/settings.png", false, NavigationType.SETTINGS);

        this.navigationItemList.add(home);
        this.navigationItemList.add(search);
        this.navigationItemList.add(messages);
        this.navigationItemList.add(profile);
        this.navigationItemList.add(settings);
    }

    public VBox getvBox() {
        return vBox;
    }

    public List<NavigationItem> getNavigationItemList(){
        return this.navigationItemList;
    }


    private class LogoutAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Logout Clicked");
            Stage stage = (Stage) vBox.getScene().getWindow();
            Parent loginView = new LoginView();
            Scene scene = new Scene(loginView, 800, 600);
            stage.setScene(scene);
            stage.show();
        }
    }
}
