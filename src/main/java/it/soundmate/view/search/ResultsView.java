/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 12:34
 * Last edited: 10/01/21, 12:34
 */

package it.soundmate.view.search;

import it.soundmate.model.Solo;
import it.soundmate.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ResultsView {

    private final SoloResults soloResults;

    public ResultsView(List<User> userList) {
        List<Solo> soloList = new ArrayList<>();
        for (User user : userList) {
            switch (user.getUserType()) {
                case SOLO:
                    soloList.add((Solo) user);
                    break;
            }
        }
        ObservableList<Solo> soloObservableList = FXCollections.observableArrayList(soloList);
        this.soloResults = new SoloResults();
        this.soloResults.setItems(soloObservableList);
    }

    public SoloResults getSoloResults() {
        return soloResults;
    }
}
