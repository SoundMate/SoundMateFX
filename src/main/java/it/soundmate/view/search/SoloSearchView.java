/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 15:09
 * Last edited: 13/01/21, 15:09
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.view.UIUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SoloSearchView extends BorderPane {
    public SoloSearchView(SoloResultBean soloResultBean) {
        UIUtils.setBackgroundPane("#232323", this);
        this.setCenter(buildResultView(soloResultBean));
    }

    private VBox buildResultView(SoloResultBean soloResultBean) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Label label = new Label(soloResultBean.getFirstName() + " " + soloResultBean.getLastName());
        vBox.getChildren().add(label);
        return vBox;
    }
}
