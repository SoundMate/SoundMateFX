package it.soundmate;

import it.soundmate.view.LoginView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SoundmateMain extends Application {
    public void start(Stage primaryStage) {
        Parent loginScreen = new LoginView();
        Scene loginScene = new Scene(loginScreen,800,600);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
