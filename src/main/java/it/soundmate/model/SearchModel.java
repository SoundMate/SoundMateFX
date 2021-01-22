package it.soundmate.model;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.bean.searchbeans.UserResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.searchengine.SearchBand;
import it.soundmate.database.searchengine.SearchRenter;
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

    /**
     * SearchByName
     *
     * Searches among Solos, Bands and Room Renters
     * considering only advanced filters.
     **/
    public List<UserResultBean> searchByName(String searchString, String[] advancedFilters) {
        List<UserResultBean> results = new ArrayList<>();
        results.addAll(searchSolos(searchString, advancedFilters));
        results.addAll(searchBands(searchString, advancedFilters[0], advancedFilters[2]));
        results.addAll(searchRooms(searchString, advancedFilters[2]));
        return results;
    }

    /**
     * Search Solos
     *
     * Create thread "SearchSolo" and call its run method
     * with parameters searchString and db connection.
     * Start the thread and then join when it's done.
     * @param searchString: String taken from the searchTextField;
     * @param advancedFilters: Advanced filters (Genre, Instrument, City);
     * @return List of the soloBeans that matches the searchString and advancedFilters, null if none.
     * */
    public List<SoloResultBean> searchSolos(String searchString, String[] advancedFilters)  {
        SearchSolo searchSolo;
        if (searchString.equals("")) {
             searchSolo = new SearchSolo(null, this.connection, advancedFilters);
        }
        else searchSolo = new SearchSolo(searchString, this.connection, advancedFilters);
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

    public List<BandResultBean> searchBands(String searchString, String genres, String city) {
        SearchBand searchBand;
        if (searchString.equals("")) {
            searchBand = new SearchBand(null, this.connection, genres, city);
        }
        else searchBand = new SearchBand(searchString, this.connection, genres, city);
        Thread searchThread = new Thread(searchBand);
        searchThread.start();
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            searchThread.interrupt();
        }
        return searchBand.getResults();
    }

    public List<RoomRenterResultBean> searchRooms(String searchString, String city) {
        SearchRenter searchRenter;
        if (searchString.equals("")) {
            searchRenter = new SearchRenter(null, this.connection, city);
        }
        else searchRenter = new SearchRenter(searchString, this.connection, city);
        Thread searchThread = new Thread(searchRenter);
        searchThread.start();
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            searchThread.interrupt();
        }
        return searchRenter.getResults();
    }
}
