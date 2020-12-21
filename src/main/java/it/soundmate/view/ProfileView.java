package it.soundmate.view;

import it.soundmate.constants.Style;
import it.soundmate.model.User;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ProfileView extends Pane {

    private final BorderPane borderPane;

    public ProfileView(User user) {
        Label userLabel = new Label(user.getFirstName() + " "+ user.getLastName());
        userLabel.setStyle(Style.HEADER_TEXT);
        this.borderPane = new BorderPane();
        this.borderPane.setCenter(userLabel);
        UIUtils.setBackgroundPane(new Image("soundmate/images/bg.png"), this.borderPane);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
