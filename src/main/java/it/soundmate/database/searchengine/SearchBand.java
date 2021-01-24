/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SearchBand implements SearchEngine<BandResultBean>, Runnable {

    private final String searchString;
    private final Connection connection;
    private final String genre;
    private final String city;
    private final List<BandResultBean> results = new ArrayList<>();

    public SearchBand(String searchString, Connection connection, String genre, String city){
        this.searchString = searchString;
        this.connection = connection;
        this.genre = genre;
        this.city = city;
    }

    @Override
    public List<BandResultBean> searchByNameAndCity(String name, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, band_name, city FROM users JOIN band ON users.id = band.id JOIN registered_users ru on users.id = ru.id WHERE LOWER(band_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?)";
        ResultSet resultSet;
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            prepareStatementGeneric(name, city, preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String encodedImg = resultSet.getString("encoded_profile_img");
                String bandName = resultSet.getString("band_name");
                String bandCity = resultSet.getString("city");
                BandResultBean resultBean = new BandResultBean(id, email, encodedImg, bandName, bandCity);
                bandResultBeanList.add(resultBean);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return bandResultBeanList;
    }

    public List<BandResultBean> searchWithGenre(String name, String genre, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, band_name, city FROM users JOIN band ON users.id = band.id JOIN registered_users ru on users.id = ru.id LEFT JOIN played_genres pg on band.id = pg.id WHERE LOWER(band_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND LOWER(genre) LIKE LOWER(?)";
        ResultSet resultSet;
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            prepareStatementWithGenre(name, city, genre, preparedStatement);
            resultSet = preparedStatement.executeQuery();
            buildBandResults(bandResultBeanList, resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return bandResultBeanList;
    }


    public List<BandResultBean> advancedSearchWithName(String name, String genre, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, genre, band_name, city FROM users join registered_users ru on ru.id = users.id JOIN band b on users.id = b.id JOIN played_genres pg on b.id = pg.id WHERE (?)= ANY(genre) AND LOWER(b.band_name) LIKE LOWER(?) AND LOWER(city) LIKE (?)";
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, genre);
            preparedStatement.setString(2, name+"%");
            preparedStatement.setString(3, Objects.requireNonNullElse(city, "%"));
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildBandResults(bandResultBeanList, resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RepositoryException("Error advanced searching solo in DB");
        }
    }

    public List<BandResultBean> advancedSearchWithNoName(String genre, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, genre, band_name, city FROM users join registered_users ru on ru.id = users.id JOIN band b on users.id = b.id JOIN played_genres pg on b.id = pg.id WHERE (?)= ANY(genre) AND LOWER(city) LIKE (?)";
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, genre);
            preparedStatement.setString(2, city);
            ResultSet resultSet = preparedStatement.executeQuery();
            return buildBandResults(bandResultBeanList, resultSet);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RepositoryException("Error advanced searching solo in DB");
        }
    }

    private List<BandResultBean> buildBandResults(List<BandResultBean> bandResultBeanList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String encodedImg = resultSet.getString("encoded_profile_img");
            String bandName = resultSet.getString("band_name");
            String bandCity = resultSet.getString("city");
            List<String> genreList;
            String [] temp = (String []) resultSet.getArray("genre").getArray();
            genreList = Arrays.asList(temp);
            BandResultBean bandResultBean = new BandResultBean(id, email, encodedImg, bandCity, bandName);
            bandResultBean.setGenres(genreList);
            bandResultBeanList.add(bandResultBean);
        }
        return bandResultBeanList;
    }


    @Override
    public void run() {
       if (this.genre == null || "NONE".equals(this.genre)) this.results.addAll(this.searchByNameAndCity(this.searchString, this.city));
       else this.results.addAll(this.searchWithGenre(this.searchString, this.genre, this.city));
    }

    public List<BandResultBean> getResults() {
        return results;
    }
}
