package it.soundmate.view.main;

import it.soundmate.constants.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HomeView extends Pane {

    private VBox homeVBox;

    public HomeView(){
        this.homeVBox = new VBox();
        this.homeVBox.setAlignment(Pos.CENTER);
        Label homeLabel = new Label("Home View");
        homeLabel.setStyle(Style.HEADER_TEXT);
        this.homeVBox.getChildren().add(homeLabel);
    }

    public VBox getHomeVBox() {
        return homeVBox;
    }
}
