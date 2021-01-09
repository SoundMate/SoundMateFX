package it.soundmate.model;

import it.soundmate.database.Connector;
import it.soundmate.database.searchengine.SearchSolo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class SearchModel {

    private final Connector connector;
    private Connection connection;

    public SearchModel() {
        this.connector = Connector.getInstance();
        try {
            this.connection = connector.getConnection();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public List<User> searchByName(String searchString) {
        List<User> results = new ArrayList<>();
        results.addAll(searchSolos(searchString));
        results.addAll(searchBands(searchString));
        results.addAll(searchRooms(searchString));  //Threads?
        return results;
    }

    public List<Solo> searchSolos(String searchString)  {
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

    public List<Band> searchBands(String searchString) {
        //Perform search in bands
        return new ArrayList<>();
    }

    public List<RoomRenter> searchRooms(String searchString) {
        //Perform search in rooms
        return new ArrayList<>();
    }
}
