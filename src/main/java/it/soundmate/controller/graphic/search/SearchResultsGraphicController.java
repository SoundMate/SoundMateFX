/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 15/01/21, 12:41
 * Last edited: 13/01/21, 15:23
 */

package it.soundmate.controller.graphic.search;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.dao.RoomRenterDao;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Room;
import it.soundmate.view.main.SearchingView;
import it.soundmate.view.search.BandSearchView;
import it.soundmate.view.search.RenterSearchView;
import it.soundmate.view.search.RoomSearchView;
import it.soundmate.view.search.SoloSearchView;

import java.util.List;

public class SearchResultsGraphicController {


    public void navigateToSoloResult(SoloResultBean soloResultBean, SearchingView searchingView, boolean comingFromSearch) {
        SoloSearchView soloResultView = new SoloSearchView(soloResultBean, searchingView, comingFromSearch);
        searchingView.setDetailViewSolo(soloResultView);
    }

    public void navigateToRenterResult(RoomRenterResultBean renterResultBean, SearchingView searchingView, boolean comingFromSearch) {
        RenterSearchView renterSearchView = new RenterSearchView(renterResultBean, comingFromSearch, searchingView);
        searchingView.setDetailViewRenter(renterSearchView);
    }

    public List<Room> fetchRenterData(RoomRenterResultBean renterResultBean) {
        RoomRenterDao roomRenterDao = new RoomRenterDao();
        try {
            return roomRenterDao.getRenterRooms(renterResultBean.getId());
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    public void navigateToRoomResult(RoomRenterResultBean renterResultBean, SearchingView searchingView, boolean comingFromSearch, Room room) {
        RoomSearchView roomSearchView = new RoomSearchView(searchingView, renterResultBean, room, comingFromSearch);
        searchingView.setDetailRoom(roomSearchView);
    }


    public void naviagateToBandResult(BandResultBean band, SearchingView searchingView, boolean b) {
        BandSearchView bandSearchView = new BandSearchView(b, band, searchingView);
        searchingView.setDetailBand(bandSearchView);
    }
}
