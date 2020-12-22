package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.uicomponents.NavigationPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProfileView extends Pane {

    private final VBox profileVBox;

    public ProfileView(User user){
        this.profileVBox = new VBox();
        this.profileVBox.setAlignment(Pos.CENTER);
        UIUtils.setBackgroundPane("#232323", this.profileVBox);
        Label userLabel = new Label(user.getFirstName()+" "+user.getLastName());
        userLabel.setStyle(Style.HEADER_TEXT);
        this.profileVBox.getChildren().add(userLabel);
    }

    public VBox getProfileVBox(){
        return this.profileVBox;
    }
}
