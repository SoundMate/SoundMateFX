/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:08
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.SoloResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSolo implements SearchEngine<SoloResultBean>, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SearchSolo.class);
    private final String searchString;
    private final Connection connection;
    private final String[] advancedFilters;
    private final List<SoloResultBean> results = new ArrayList<>();
    private final String city;

    public SearchSolo(String searchString, Connection connection, String[] advancedFilters){
        this.searchString = searchString;
        this.connection = connection;
        this.advancedFilters = advancedFilters;
        this.city = advancedFilters[2];
    }

    @Override
    public List<SoloResultBean> searchByNameAndCity(String name, String city) {
        return null;
    }

    /**
     * To be implemented better
     * Use a get Method from the soloDao to get all the Solo info
     * or a get Method specific for the search (avoiding unnecessary data like password)
     * like below
     * @param name: searchString to be searched
     * @return List of SoloResultBean found
     */
    public List<SoloResultBean> search(String name, String city, String sql) {
        logger.info("Searching solos with name {} city {}", name, city);
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            prepareStatementWithFilters(name, city, this.advancedFilters[0], this.advancedFilters[1], preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return soloResults;
    }

    public List<SoloResultBean> searchWithFilters(String name, String city, String genre, String instrument) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id JOIN played_instruments pi on s.id = pi.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND (?) LIKE ANY(genre) AND (?) LIKE ANY(instruments)";
        logger.info("Searching solos with name {} city {} genre {} instrument {}", name, city, genre, instrument);
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            prepareStatementWithFilters(name, city, genre, instrument, preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return soloResults;
    }

    private void prepareStatementWithFilters(String name, String city, String genre, String instrument, PreparedStatement preparedStatement) throws SQLException {
        prepareStatementGeneric(name, city, preparedStatement);
        if (genre != null && !genre.equals("NONE")) preparedStatement.setString(3, genre);
        if (instrument != null && !instrument.equals("NONE")) preparedStatement.setString(4, instrument);
    }


    private void buildSoloResult(ResultSet resultSet, List<SoloResultBean> soloResultBeans) throws SQLException {
        logger.info("Building solo result");
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String encodedImg = resultSet.getString("encoded_profile_img");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String soloCity = resultSet.getString("city");
        List<String> genreList = new ArrayList<>();
        if (!this.advancedFilters[0].equals("NONE")) {
            if (resultSet.getArray("genre") == null) genreList = new ArrayList<>();
            else {
                String [] temp = (String []) resultSet.getArray("genre").getArray();
                genreList = Arrays.asList(temp);
            }
        }

        List<String> instrumentList = new ArrayList<>();
        if (!this.advancedFilters[1].equals("NONE")) {
            if (resultSet.getArray("instrument") != null) {
                String [] temp = (String []) resultSet.getArray("instrument").getArray();
                instrumentList = Arrays.asList(temp);
            } else instrumentList = new ArrayList<>();
        }

        SoloResultBean soloResultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName, soloCity);
        soloResultBean.setGenres(genreList);
        soloResultBean.setInstrumentList(instrumentList);
        logger.info("Result found: NAME: {} {}, CITY: {}", soloResultBean.getFirstName(), soloResultBean.getLastName(), soloResultBean.getCity());
        soloResultBeans.add(soloResultBean);
    }

    @Override
    public void run() {
        String sql = determineStatement(this.advancedFilters[0], this.advancedFilters[1]);
        logger.info("Running solo search with NAME: {} CITY: {}",this.searchString, this.city);
        results.addAll(this.search(this.searchString, this.city, sql));

    }

    private String determineStatement(String genre, String instrument) {
        StringBuilder stringBuilder = new StringBuilder();
        String basicStatement = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?)";
        stringBuilder.append(basicStatement);
        if (genre != null && !genre.equals("NONE")) {
            stringBuilder.append(" AND ").append(genre).append(" LIKE ANY(genre)");
        }
        if (instrument != null && !instrument.equals("NONE")) {
            stringBuilder.append(" AND ").append(instrument).append(" LIKE ANY(instruments)");
        }
        return stringBuilder.toString();
    }

    public List<SoloResultBean> getResults() {
        return results;
    }
}
