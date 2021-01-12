package it.soundmate.model;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.searchengine.SearchSolo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class SearchModel {

    private Connection connection;

    public SearchModel() {
        Connector connector = Connector.getInstance();
        try {
            this.connection = connector.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<UserResultBean> searchByName(String searchString) {
        List<UserResultBean> results = new ArrayList<>();
        results.addAll(searchSolos(searchString));
        results.addAll(searchBands(searchString));
        results.addAll(searchRooms(searchString));  //Threads?
        return results;
    }

    public List<SoloResultBean> searchSolos(String searchString)  {
        SearchSolo searchSolo = new SearchSolo(searchString, this.connection);
        Thread searchThread = new Thread(searchSolo);
        searchThread.start();
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            searchThread.interrupt();
        }
        return searchSolo.getResults();
    }

    public List<BandResultBean> searchBands(String searchString) {
        //Perform search in bands
        return new ArrayList<>();
    }

    public List<RoomRenterResultBean> searchRooms(String searchString) {
        //Perform search in rooms
        return new ArrayList<>();
    }
}
