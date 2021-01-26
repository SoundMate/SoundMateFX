/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 26/01/21, 15:12
 * Last edited: 26/01/21, 15:12
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.constants.Style;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.SearchingView;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class RenterMapView extends VBox {

    private final RoomRenterResultBean roomRenterResultBean;
    private final SearchingView searchingView;
    private final boolean comingFromSearch;

    //UI
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();
    private final Button backBtn = UIUtils.createStyledButton("Back", new BackAction());

    public RenterMapView(RoomRenterResultBean roomRenterResultBean, SearchingView searchingView, boolean comingFromSearch) {
        this.roomRenterResultBean = roomRenterResultBean;
        this.searchingView = searchingView;
        this.comingFromSearch = comingFromSearch;
        this.buildMapVBox(roomRenterResultBean);
    }

    private void buildMapVBox(RoomRenterResultBean roomRenterResultBean) {
        Label nameLabel = new Label(roomRenterResultBean.getName());
        nameLabel.setStyle(Style.HEADER_TEXT);
        nameLabel.setPadding(new Insets(25, 0, 25, 25));
        this.getChildren().add(nameLabel);
        HBox padding = new HBox(this.backBtn);
        padding.setPadding(new Insets(0, 0, 0, 25));
        this.getChildren().add(padding);

        webEngine.load("http://www.google.com");
        this.getChildren().add(webView);
    }


    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            searchingView.setDetailViewRenter(new RenterSearchView(roomRenterResultBean, comingFromSearch, searchingView));
        }
    }
}
