package it.soundmate.model;

import it.soundmate.database.Connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class SearchEngine {

    private final Connector connector;
    private Connection connection;

    public SearchEngine() {
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
        //results.addAll(searchBands(searchString));  Band deve estendere User (?)
        results.addAll(searchRooms(searchString));  //Threads?
        return results;
    }

    public List<Solo> searchSolos(String searchString) {
        //TODO: Perform search in solos
        return new ArrayList<>();
    }

    public List<Band> searchBands(String searchString) {
        //TODO: Perform search in bands
        return new ArrayList<>();
    }

    public List<RoomRenter> searchRooms(String searchString) {
        //TODO: Perform search in rooms
        return new ArrayList<>();
    }
}
