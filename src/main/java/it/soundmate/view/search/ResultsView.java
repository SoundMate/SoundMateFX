/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 10/01/21, 12:34
 * Last edited: 10/01/21, 12:34
 */

package it.soundmate.view.search;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.exceptions.InputException;
import it.soundmate.model.User;
import it.soundmate.view.main.SearchingView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class ResultsView {

    private final SoloResults soloResults;
    private final RenterResults renterResults;
    private final BandResults bandResults;

    public ResultsView(List<UserResultBean> userList, SearchingView searchingView, User searcher) {
        List<SoloResultBean> soloList = new ArrayList<>();
        List<RoomRenterResultBean> roomRenterResultBeanList = new ArrayList<>();
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        for (UserResultBean user : userList) {
            if (user.getId() == searcher.getId()) continue;
            switch (user.getUserType()) {
                case SOLO:
                    user.setSearcher(searcher);
                    soloList.add((SoloResultBean) user);
                    break;
                case ROOM_RENTER:
                    user.setSearcher(searcher);
                    roomRenterResultBeanList.add((RoomRenterResultBean) user);
                    break;
                case BAND:
                    user.setSearcher(searcher);
                    bandResultBeanList.add((BandResultBean) user);
                    break;
                default:
                    throw new InputException("No results found, input ex");
            }
        }
        ObservableList<SoloResultBean> soloObservableList = FXCollections.observableArrayList(soloList);
        ObservableList<RoomRenterResultBean> renterResultBeanObservableList = FXCollections.observableArrayList(roomRenterResultBeanList);
        ObservableList<BandResultBean> bandResultBeanObservableList = FXCollections.observableArrayList(bandResultBeanList);
        this.soloResults = new SoloResults(searchingView);
        this.renterResults = new RenterResults(searchingView);
        this.bandResults = new BandResults(searchingView);
        this.soloResults.setItems(soloObservableList);
        this.bandResults.setItems(bandResultBeanObservableList);
        this.renterResults.setItems(renterResultBeanObservableList);
    }

    public static VBox buildResultVBox() {
        VBox resultVBox = new VBox();
        resultVBox.setAlignment(Pos.CENTER);
        resultVBox.setPadding(new Insets(5));
        resultVBox.setSpacing(10);
        resultVBox.setPrefHeight(USE_COMPUTED_SIZE);
        resultVBox.setPrefWidth(USE_COMPUTED_SIZE);
        return resultVBox;
    }


    public SoloResults getSoloResults() {
        return soloResults;
    }

    public RenterResults getRenterResults(){
        return renterResults;
    }

    public BandResults getBandResult() {
        return bandResults;
    }
}
