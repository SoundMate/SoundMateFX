/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 13:50
 * Last edited: 25/01/21, 13:50
 */

package it.soundmate.view.main;

import it.soundmate.view.search.SoloSearchView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class SearchingView extends Pane {

    public void setDetailViewSolo(SoloSearchView soloSearchView, VBox contentVBox) {
        contentVBox.getChildren().set(0, soloSearchView);
    }

    public abstract VBox getContentVBox();

}
