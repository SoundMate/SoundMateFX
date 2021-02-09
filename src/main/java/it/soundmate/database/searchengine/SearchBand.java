/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.BandResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchBand implements SearchEngine<BandResultBean>, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SearchBand.class);

    private final String searchString;
    private final Connection connection;
    private final String genre;
    private final String city;
    private final List<BandResultBean> results = new ArrayList<>();
    private static final String ERRORS = "Error Search";

    public SearchBand(String searchString, Connection connection, String genre, String city){
        if (searchString == null) this.searchString = "";
        else this.searchString = searchString;
        this.connection = connection;
        this.genre = genre;
        this.city = city;
    }

    @Override
    public List<BandResultBean> search(String name, String city) {
        logger.info("Searching with name: {}, city: {}, genre: {}",name, city, genre);
        String sql = "SELECT users.id, email, encoded_profile_img, band_name, city, genre FROM users JOIN band ON users.id = band.id JOIN registered_users ru on users.id = ru.id JOIN played_genres pg on band.id = pg.id WHERE LOWER(band_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?)";
        ResultSet resultSet;
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            prepareStatementGeneric(name, city, preparedStatement);
            resultSet = preparedStatement.executeQuery();
            return buildBandResults(bandResultBeanList, resultSet);
        } catch (SQLException sqlException) {
            logger.error(ERRORS, sqlException);
        }
        return bandResultBeanList;
    }

    private List<BandResultBean> searchWithGenre(String name, String city, String genre) {
        logger.info("Searching with name: {}, city: {}, genre: {}",name, city, genre);
        String sql = "SELECT users.id, email, encoded_profile_img, band_name, city, genre FROM users JOIN band ON users.id = band.id JOIN registered_users ru on users.id = ru.id JOIN played_genres pg on band.id = pg.id WHERE LOWER(band_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND (?) LIKE ANY(genre)";
        ResultSet resultSet;
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+"%");
            preparedStatement.setString(2, city+"%");
            preparedStatement.setString(3, genre);
            resultSet = preparedStatement.executeQuery();
            return buildBandResults(bandResultBeanList, resultSet);
        } catch (SQLException sqlException) {
            logger.error(ERRORS, sqlException);
            return new ArrayList<>();
        }
    }

    private List<BandResultBean> buildBandResults(List<BandResultBean> bandResultBeanList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String encodedImg = resultSet.getString("encoded_profile_img");
            String bandName = resultSet.getString("band_name");
            String bandCity = resultSet.getString("city");
            BandResultBean bandResultBean = new BandResultBean(id, email, encodedImg, bandName, bandCity);
            List<String> genreList;
            if (resultSet.getArray("genre") == null) genreList = new ArrayList<>();
            else {
                String [] temp = (String []) resultSet.getArray("genre").getArray();
                genreList = Arrays.asList(temp);
            }
            logger.info("Band ID: {}", id);
            bandResultBean.setGenres(genreList);
            bandResultBeanList.add(bandResultBean);
            logger.info("Building result");
        }
        return bandResultBeanList;
    }


    @Override
    public void run() {
       if (this.genre == null || "NONE".equals(this.genre)) this.results.addAll(this.search(this.searchString, this.city));
       else this.results.addAll(this.searchWithGenre(this.searchString, this.city, this.genre));
    }

    public List<BandResultBean> getResults() {
        return results;
    }
}
