package it.soundmate.view.main;

import it.soundmate.constants.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SearchView extends Pane {

    private VBox searchVBox;

    public SearchView(){
        this.searchVBox = new VBox();
        this.searchVBox.setAlignment(Pos.CENTER);
        Label searchLabel = new Label("Search View");
        searchLabel.setStyle(Style.HEADER_TEXT);
        this.searchVBox.getChildren().add(searchLabel);
    }

    public VBox getSearchVBox() {
        return searchVBox;
    }
}
