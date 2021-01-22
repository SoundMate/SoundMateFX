/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 18:13
 * Last edited: 13/01/21, 18:07
 */

package it.soundmate.controller.graphic.register;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.model.User;
import it.soundmate.view.main.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface RegisterGraphicController {

    default void navigateToMainView(User user, Stage stage){
        Parent mainView = new MainView(user).getBorderPane();
        Scene scene = new Scene(mainView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    User registerUser(RegisterBean registerBean);
}
