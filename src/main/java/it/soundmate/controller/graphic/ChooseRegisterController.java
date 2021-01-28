/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 11/01/21, 16:22
 * Last edited: 11/01/21, 16:17
 */

package it.soundmate.controller.graphic;

import it.soundmate.model.UserType;
import it.soundmate.view.LoginView;
import it.soundmate.view.registerview.RegisterView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Choose Register Controller
 *
 * Acts as the graphic controller for the ChooseRegisterView.
 * Contains methods to update the view.
 * */
public class ChooseRegisterController {

    public void navigateToRegisterSoloView(Stage stage) {
        Parent registerView = new RegisterView(UserType.SOLO).getMainBorderPane();
        Scene scene = new Scene(registerView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToRegisterBandView(Stage stage) {
        Parent registerView = new RegisterView(UserType.BAND).getMainBorderPane();
        Scene scene = new Scene(registerView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToRegisterRoomRenterView(Stage stage) {
        Parent registerView = new RegisterView(UserType.ROOM_RENTER).getMainBorderPane();
        Scene scene = new Scene(registerView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void backToLoginView(Stage stage) {
        Parent loginView = new LoginView();
        Scene scene = new Scene(loginView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

}
