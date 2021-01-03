package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.controller.SearchController;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchView extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(SearchView.class);
    private final SearchController searchController = new SearchController();

    public BorderPane getSearchBorderPane() {
        return searchBorderPane;
    }

    private final BorderPane searchBorderPane;
    private final TextField searchTextField = new TextField();
    private final Button searchBtn = new Button();
    private final Button searchOnMapBtn = new Button();
    private final List<RadioButton> filters = new ArrayList<>();
    private final ScrollPane resultsScrollPane = new ScrollPane();
    private final VBox resultsVBox = new VBox();

    public SearchView(){
        this.searchBorderPane = new BorderPane();
        UIUtils.setBackgroundPane("#232323", this.searchBorderPane);

        Node top = buildTopSearchBar();
        this.searchBorderPane.setTop(top);
        this.searchBorderPane.setCenter(resultsScrollPane);

        this.resultsScrollPane.setStyle("-fx-background: #232323; -fx-background-color: #232323; -fx-border-color: #232323");
        this.resultsScrollPane.setContent(this.resultsVBox);
        this.resultsScrollPane.setPrefViewportWidth(Region.USE_COMPUTED_SIZE);
        this.resultsScrollPane.setPrefViewportHeight(Region.USE_COMPUTED_SIZE);
        this.resultsVBox.setPadding(new Insets(25));
        this.resultsVBox.setAlignment(Pos.CENTER);
        this.resultsVBox.setPrefHeight(USE_COMPUTED_SIZE);
        this.resultsVBox.setPrefWidth(USE_COMPUTED_SIZE);
    }

    private Node buildTopSearchBar() {
        VBox topVBox = new VBox();
        topVBox.setSpacing(10);
        topVBox.setPadding(new Insets(25));
        topVBox.setAlignment(Pos.CENTER);
        topVBox.setPrefWidth(USE_COMPUTED_SIZE);
        topVBox.setPrefHeight(USE_COMPUTED_SIZE);

        Label searchLabel = new Label("Search");
        searchLabel.setStyle(Style.HEADER_TEXT);

        HBox searchBarHBox = buildSearchBarHBox();
        HBox filtersHBox = buildFiltersHBox();

        topVBox.getChildren().addAll(searchLabel, searchBarHBox, filtersHBox);
        return topVBox;
    }

    private HBox buildFiltersHBox() {
        HBox filtersHBox = new HBox();
        filtersHBox.setPrefHeight(USE_COMPUTED_SIZE);
        filtersHBox.setPrefWidth(USE_COMPUTED_SIZE);
        filtersHBox.setAlignment(Pos.CENTER);
        filtersHBox.setSpacing(20);

        this.filters.add(UIUtils.createRadioButton("Musicians"));
        this.filters.add(UIUtils.createRadioButton("Bands"));
        this.filters.add(UIUtils.createRadioButton("Rooms"));

        Label filterLabel = new Label("Filter Results");
        filterLabel.setStyle(Style.MID_LABEL);

        this.searchOnMapBtn.setText("Search On Map");
        this.searchOnMapBtn.setStyle(Style.BTN_STYLE_1);

        filtersHBox.getChildren().add(filterLabel);
        filtersHBox.getChildren().addAll(this.filters);
        UIUtils.addRegion(null, filtersHBox);
        filtersHBox.getChildren().add(this.searchOnMapBtn);
        return filtersHBox;
    }

    private HBox buildSearchBarHBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(USE_COMPUTED_SIZE);
        hBox.setPrefHeight(USE_COMPUTED_SIZE);

        this.searchTextField.setStyle(Style.TEXT_FIELD);
        this.searchTextField.setPromptText("Search...");
        this.searchBtn.setText("Search");
        this.searchBtn.setStyle(Style.BTN_STYLE_1);
        this.searchBtn.setOnAction(new SearchAction());

        hBox.getChildren().addAll(this.searchTextField, this.searchBtn);
        HBox.setHgrow(this.searchTextField, Priority.ALWAYS);
        return hBox;
    }


    private class SearchAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (!resultsVBox.getChildren().isEmpty()) {  //Rimuove label precedenti tipo "No results Found" quando si fa una nuova ricerca
                resultsVBox.getChildren().remove(0);
            }
            logger.info("Search Clicked");
            if (searchTextField.getText().isEmpty()) {  //Se la textfield è vuota non fa nessuna ricerca
                logger.error("Empty text fields");
                searchTextField.setPromptText("Try to search for Musicians, Bands or Rooms...");
                return;
            }

            Label loadingLabel = new Label("Loading results...");
            loadingLabel.setStyle(Style.LOW_LABEL);
            resultsVBox.getChildren().add(loadingLabel);

            List<User> results = searchController.performSearch(searchTextField.getText(), filters);
            logger.info("Done Search");
            if (results == null || results.isEmpty()) {
                Label noResultsLabel = new Label("No Results Found");
                noResultsLabel.setStyle(Style.MID_LABEL);
                resultsVBox.getChildren().remove(loadingLabel);
                resultsVBox.getChildren().add(noResultsLabel);
            }
        }
    }
}
