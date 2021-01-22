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

    /**
     * To be implemented better
     * Use a get Method from the soloDao to get all the Solo info
     * or a get Method specific for the search (avoiding unnecessary data like password)
     * like below
     * @param name: searchString to be searched
     * @return List of SoloResultBean found
     */
    @Override
    public List<SoloResultBean> searchByNameAndCity(String name, String city) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, city, genre FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id LEFT JOIN fav_genres fg on s.id = fg.id WHERE LOWER(s.first_name) LIKE LOWER(?) AND LOWER(city) LIKE LOWER(?)";
        logger.info("Searching solos with name {} city {}", name, city);
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            ResultSet resultSet = prepareBasicStatement(name, city, preparedStatement);
            while (resultSet.next()) {
                buildSoloResult(resultSet, soloResults);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return soloResults;
    }


    private void buildSoloResult(ResultSet resultSet, List<SoloResultBean> soloResultBeans) throws SQLException {
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
        SoloResultBean soloResultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName, soloCity);
        soloResultBean.setGenres(genreList);
        logger.info("Result found: NAME: {} {}, CITY: {}", soloResultBean.getFirstName(), soloResultBean.getLastName(), soloResultBean.getCity());
        soloResultBeans.add(soloResultBean);
    }

    @Override
    public void run() {
        logger.info("Running solo search with NAME: {} CITY: {}",this.searchString, this.city);
        results.addAll(this.searchByNameAndCity(this.searchString, this.city));
    }

    public List<SoloResultBean> getResults() {
        return results;
    }
}
