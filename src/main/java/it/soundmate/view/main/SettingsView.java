package it.soundmate.view.main;

import it.soundmate.constants.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SettingsView extends Pane {

    private VBox settingsVBox;

    public SettingsView(){
        this.settingsVBox = new VBox();
        this.settingsVBox.setAlignment(Pos.CENTER);
        Label homeLabel = new Label("Settings View");
        homeLabel.setStyle(Style.HEADER_TEXT);
        this.settingsVBox.getChildren().add(homeLabel);
    }

    public VBox getSettingsVBox() {
        return settingsVBox;
    }
}
