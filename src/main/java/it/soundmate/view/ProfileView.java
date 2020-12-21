package it.soundmate.view;

import it.soundmate.model.User;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ProfileView extends BorderPane {
    public ProfileView(User user) {
        Label userLabel = new Label(user.getFirstName() + " "+ user.getLastName());
    }
}
