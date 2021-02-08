/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:08
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.SoloResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
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
    private static final String ERRORSRC = "Error Search";

    public SearchSolo(String searchString, Connection connection, String[] advancedFilters){
        this.searchString = searchString;
        this.connection = connection;
        this.advancedFilters = advancedFilters;
        this.city = advancedFilters[2];
    }

    /**
     * To be implemented better
     * Use a get Method from the soloDao to get all the Solo info
     * or a get Method specific for the search (avoiding unnecessary data like password)
     * like below
     * @param name: searchString to be searched
     * @return List of SoloResultBean found
     */
    @Override
    public List<SoloResultBean> search(String name, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city, genre, instruments FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id JOIN played_instruments pi on s.id = pi.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?)";
        logger.info("Searching solos with name {} city {}", name, city);
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name+'%');
            preparedStatement.setString(2, city+'%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
        } catch (SQLException sqlException) {
            logger.error(ERRORSRC, sqlException);
        }
        return soloResults;
    }

    public List<SoloResultBean> searchWithGenre(String name, String city, String genre) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city, genre, instruments FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id JOIN played_instruments pi on s.id = pi.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND (?) LIKE ANY(genre)";
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+'%');
            preparedStatement.setString(2, city+'%');
            preparedStatement.setString(3, genre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
            return soloResults;
        } catch (SQLException sqlException) {
            logger.info("SQL Exception searching with genre: {}", sqlException.getMessage());
            return new ArrayList<>();
        }
    }


    private void buildSoloResult(ResultSet resultSet, List<SoloResultBean> soloResultBeans) throws SQLException {
        logger.info("Working Directory = {}", Paths.get(".").toAbsolutePath().normalize());
        logger.info("Building solo result");
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String encodedImg = resultSet.getString("encoded_profile_img");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String soloCity = resultSet.getString("city");
        List<String> genreList;
        if (resultSet.getArray("genre") == null) genreList = new ArrayList<>();
        else {
            String [] temp = (String []) resultSet.getArray("genre").getArray();
            genreList = Arrays.asList(temp);
        }

        List<String> instrumentList;
        if (resultSet.getArray("instruments") != null) {
            String [] temp = (String []) resultSet.getArray("instruments").getArray();
            instrumentList = Arrays.asList(temp);
        } else instrumentList = new ArrayList<>();

        SoloResultBean soloResultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName, soloCity);
        soloResultBean.setGenres(genreList);
        soloResultBean.setInstrumentList(instrumentList);
        logger.info("Result found: NAME: {} {}, CITY: {}", soloResultBean.getFirstName(), soloResultBean.getLastName(), soloResultBean.getCity());
        soloResultBeans.add(soloResultBean);
    }

    @Override
    public void run() {
        if (!this.advancedFilters[0].equals("NONE") && !this.advancedFilters[1].equals("NONE")) {
            //GENRE AND INSTRUMENT FILTER
            this.results.addAll(this.searchWithGenreAndInstrument(this.searchString, this.city, this.advancedFilters[0], this.advancedFilters[1]));
        } else if (!this.advancedFilters[0].equals("NONE")) {
            //ONLY GENRE FILTER
            this.results.addAll(this.searchWithGenre(this.searchString, this.city, this.advancedFilters[0]));
        } else if (!this.advancedFilters[1].equals("NONE")){
            //ONLY INSTRUMENT FILTER
            this.results.addAll(this.searchWithInstrument(this.searchString, this.city, this.advancedFilters[1]));
        } else {
            this.results.addAll(this.search(this.searchString, this.city));
        }
    }

    private List<SoloResultBean> searchWithInstrument(String name, String city, String instrument) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city, genre, instruments FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id JOIN played_instruments pi on s.id = pi.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND (?) LIKE ANY(instruments)";
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+'%');
            preparedStatement.setString(2, city+'%');
            preparedStatement.setString(3, instrument);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
            return soloResults;
        } catch (SQLException sqlException) {
            logger.info("SQL Exception searching with instrument: {}", sqlException.getMessage());
            return new ArrayList<>();
        }
    }

    private List<SoloResultBean> searchWithGenreAndInstrument(String name, String city, String genre, String instrument) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city, genre, instruments FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id JOIN played_instruments pi on s.id = pi.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?) AND (?) LIKE ANY(genre) AND (?) LIKE ANY(instruments)";
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+'%');
            preparedStatement.setString(2, city+'%');
            preparedStatement.setString(3, genre);
            preparedStatement.setString(4, instrument);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
            return soloResults;
        } catch (SQLException sqlException) {
            logger.info("SQL Exception searching with genre: {}", sqlException.getMessage());
            return new ArrayList<>();
        }
    }

    public List<SoloResultBean> getResults() {
        return results;
    }
}
