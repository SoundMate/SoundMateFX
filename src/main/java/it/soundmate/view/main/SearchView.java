package it.soundmate.view.main;


import it.soundmate.view.UIUtils;
import it.soundmate.view.search.DefaultSearchView;
import it.soundmate.view.search.MapSearchView;
import it.soundmate.view.search.SoloSearchView;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SearchView extends Pane {

    private static SearchView instance = null;
    private static final Logger logger = LoggerFactory.getLogger(SearchView.class);

    private BorderPane searchBorderPane;
    private VBox contentVBox;

    public static SearchView getInstance(){
        if (instance == null) {
            instance = new SearchView();
        }
        return instance;
    }

    public BorderPane getSearchBorderPane() {
        return searchBorderPane;
    }

    public VBox getContentVBox() {
        return contentVBox;
    }

    public SearchView(){
        this.contentVBox = new VBox();
        this.contentVBox.setAlignment(Pos.TOP_CENTER);
        UIUtils.setBackgroundPane("#232323", this.contentVBox);
        this.searchBorderPane = new DefaultSearchView().getDefaultSVBorderPane();
        this.contentVBox.getChildren().add(this.searchBorderPane);
    }

    public void setMapView() {
        this.contentVBox.getChildren().set(0, new MapSearchView().getSearchMapBorderPane());
        logger.info("Search Map Page set");
    }

    public void setDefaultView() {
        this.contentVBox.getChildren().set(0, this.searchBorderPane);
        logger.info("Default Search Page set");
    }

    public void setDetailViewSolo(SoloSearchView soloSearchView) {
        this.contentVBox.getChildren().set(0, soloSearchView);
    }
}
