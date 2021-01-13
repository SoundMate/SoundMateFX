/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 18:13
 * Last edited: 13/01/21, 18:07
 */

package it.soundmate.controller.graphic.register;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.model.User;
import it.soundmate.view.main.MainView;
import it.soundmate.view.registerview.ChooseRegisterView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class RegisterGraphicController {

    public static void backNavigation(Stage stage) {
        Parent chooseRegisterView = new ChooseRegisterView();
        Scene scene = new Scene(chooseRegisterView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void navigateToMainView(User user, Stage stage){
        Parent mainView = new MainView(user).getBorderPane();
        Scene scene = new Scene(mainView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public abstract User registerUser(RegisterBean registerBean);
}
