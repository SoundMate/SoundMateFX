/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 12:34
 * Last edited: 10/01/21, 12:34
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.view.main.SearchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ResultsView {

    private final SoloResults soloResults;

    public ResultsView(List<UserResultBean> userList, SearchView searchView) {
        List<SoloResultBean> soloList = new ArrayList<>();
        for (UserResultBean user : userList) {
            switch (user.getUserType()) {
                case SOLO:
                    soloList.add((SoloResultBean) user);
                    break;
                case ROOM_RENTER:
                case BAND:
                    break;
            }
        }
        ObservableList<SoloResultBean> soloObservableList = FXCollections.observableArrayList(soloList);
        this.soloResults = new SoloResults(searchView);
        this.soloResults.setItems(soloObservableList);
    }

    public SoloResults getSoloResults() {
        return soloResults;
    }
}
