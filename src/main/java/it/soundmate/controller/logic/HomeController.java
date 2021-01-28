/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 25/01/21, 20:42
 * Last edited: 25/01/21, 20:42
 */

package it.soundmate.controller.logic;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.model.SearchModel;
import it.soundmate.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeController {

    private final SearchModel searchModel = new SearchModel();
    private final User user;

    public HomeController(User user){
        this.user = user;
    }

    public List<SoloResultBean> searchHomeSolos(String city) {
        String[] filters = new String[3];
        filters[0] = "NONE";
        filters[1] = "NONE";
        filters[2] = city;
        List<SoloResultBean> results = searchModel.searchSolos("", filters);
        results.removeIf(soloResultBean -> soloResultBean.getId() == this.user.getId());
        for (SoloResultBean soloResultBean: results) {
            soloResultBean.setSearcher(this.user);
        }
        return results;
    }

    public List<BandResultBean> searchHomeBands(String city) {
        List<BandResultBean> bandResultBeanList;
        bandResultBeanList = new ArrayList<>(searchModel.searchBands("", "NONE", city));
        bandResultBeanList.removeIf(soloResultBean -> soloResultBean.getId() == this.user.getId());
        for (BandResultBean bandResultBean: bandResultBeanList) {
            bandResultBean.setSearcher(this.user);
        }
        return bandResultBeanList;
    }

    public List<RoomRenterResultBean> searchHomeRenters(String city) {
        List<RoomRenterResultBean> roomRenterResultBeanList;
        roomRenterResultBeanList = new ArrayList<>(searchModel.searchRooms("", city));
        roomRenterResultBeanList.removeIf(roomRenterResultBean -> roomRenterResultBean.getId() == this.user.getId());
        for (RoomRenterResultBean roomRenterResultBean: roomRenterResultBeanList) {
            roomRenterResultBean.setSearcher(this.user);
        }
        return roomRenterResultBeanList;
    }
}
