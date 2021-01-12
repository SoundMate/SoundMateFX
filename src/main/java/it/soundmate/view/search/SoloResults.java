/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 12:34
 * Last edited: 10/01/21, 12:34
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.model.Solo;
import it.soundmate.view.UIUtils;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SoloResults extends ListView<SoloResultBean> {

    private static final Logger logger = LoggerFactory.getLogger(SoloResults.class);

    public SoloResults() {
        this.setCellFactory(param -> new SoloResult());
        this.setOrientation(Orientation.HORIZONTAL);
        this.setPrefHeight(100);
        this.setPrefWidth(525);
    }

    public boolean isEmpty() {
        return this.getItems().isEmpty();
    }


    public static class SoloResult extends ListCell<SoloResultBean> {
        @Override
        protected void updateItem(SoloResultBean solo, boolean empty) {
            super.updateItem(solo, empty);
            if (solo != null) {
                VBox vBox = buildResultVBox(solo);
                this.setStyle("-fx-background-color: #232323;");
                setGraphic(vBox);
            } else {
                this.setStyle("-fx-background-color: #232323");
            }
        }

        @NotNull
        private VBox buildResultVBox(SoloResultBean solo) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.setPadding(new Insets(5));
            vBox.setSpacing(10);
            vBox.setPrefHeight(50);
            vBox.setPrefWidth(USE_COMPUTED_SIZE);
            Label name = new Label(solo.getFirstName()+" "+ solo.getLastName());
            name.setStyle(Style.MID_LABEL);
            Button btn = UIUtils.createStyledButton("View Profile", new SelectedSoloResult(solo));
            vBox.getChildren().addAll(name, btn);
            return vBox;
        }

        private static class SelectedSoloResult implements EventHandler<ActionEvent> {

            private final SoloResultBean solo;

            public SelectedSoloResult(SoloResultBean solo){
                this.solo = solo;
            }

            @Override
            public void handle(ActionEvent event) {
                logger.info("Item selected {} {}", solo.getFirstName(), solo.getLastName());
            }
        }
    }

}
