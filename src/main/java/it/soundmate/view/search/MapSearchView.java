package it.soundmate.view.search;

import it.soundmate.constants.Style;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MapSearchView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(MapSearchView.class);

    private BorderPane searchMapBorderPane;
    private TextField searchMapTextField = new TextField();
    private Button searchMapBtn = UIUtils.createStyledButton("Search on Map", new SearchOnMapAction());

    public MapSearchView() {
        this.searchMapBorderPane = buildSearchMapBorderPane();
    }

    public BorderPane getSearchMapBorderPane() {
        return searchMapBorderPane;
    }

    private BorderPane buildSearchMapBorderPane() {
        BorderPane borderPane = new BorderPane();
        UIUtils.setBackgroundPane("#232323", borderPane);
        Node top = buildTopNode();
        Node center = buildCenterNode();
        borderPane.setTop(top);
        borderPane.setCenter(center);
        return borderPane;
    }

    private Node buildCenterNode() {
        return null;
    }

    private Node buildTopNode() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(25));

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setStyle(Style.BLACK_BTN);
        backButton.setOnAction(new BackAction());

        this.searchMapTextField.setStyle(Style.TEXT_FIELD);
        hBox.getChildren().addAll(backButton, this.searchMapTextField, this.searchMapBtn);

        Label label = new Label("Search On Map");
        label.setStyle(Style.HEADER_TEXT);

        vBox.getChildren().addAll(label, hBox);
        return vBox;
    }


    private class SearchOnMapAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Search On Map Clicked");
        }
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Back Clicked");
            SearchView.getInstance().setDefaultView();
        }
    }
}
