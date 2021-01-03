package it.soundmate.model;

import it.soundmate.database.Connector;

import java.sql.Connection;
import java.util.ArrayList;

import java.util.List;

public class SearchEngine {

    private final Connector connector;
    private final Connection connection;

    public SearchEngine() {
        this.connector = Connector.getInstance();
        this.connection = connector.getConnection();
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
        List<Solo> resultsSolo = new ArrayList<>();
        return resultsSolo;
    }

    public List<Band> searchBands(String searchString) {
        //TODO: Perform search in bands
        List<Band> resultsBand = new ArrayList<>();
        return resultsBand;
    }

    public List<RoomRenter> searchRooms(String searchString) {
        //TODO: Perform search in rooms
        List<RoomRenter> resultsRoom = new ArrayList<>();
        return resultsRoom;
    }
}
