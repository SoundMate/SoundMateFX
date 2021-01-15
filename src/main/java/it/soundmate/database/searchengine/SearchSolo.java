/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:08
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSolo implements SearchEngine<SoloResultBean>, Runnable {

    private final String searchString;
    private final Connection connection;
    private final String[] advancedFilters;
    private final List<SoloResultBean> results = new ArrayList<>();

    public SearchSolo(String searchString, Connection connection, String[] advancedFilters){
        this.searchString = searchString;
        this.connection = connection;
        this.advancedFilters = advancedFilters;
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
    public List<SoloResultBean> searchByName(String name) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name FROM users JOIN solo s on users.id = s.id JOIN registered_users ru on users.id = ru.id WHERE LOWER(s.first_name) LIKE LOWER(?)";
        ResultSet resultSet;
        List<SoloResultBean> soloResults = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+"%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String encodedImg = resultSet.getString("encoded_profile_img");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                SoloResultBean resultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName);
                soloResults.add(resultBean);
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return soloResults;
    }

    //Per ora solo generi (Da aggiungere colonne in SOLO per strumenti e città
    public List<SoloResultBean> advancedSearchWithNoName(String genre) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, genre FROM users join registered_users ru on ru.id = users.id JOIN solo s on users.id = s.id JOIN fav_genres fg on s.id = fg.id WHERE (?)= ANY(genre);";
        return getSoloResultBeans(genre, sql, null);
    }

    public List<SoloResultBean> advancedSearchWithName(String name, String genre) {
        String sql = "SELECT users.id, email, encoded_profile_img, first_name, last_name, genre FROM users join registered_users ru on ru.id = users.id JOIN solo s on users.id = s.id JOIN fav_genres fg on s.id = fg.id WHERE (?)= ANY(genre) AND LOWER(s.first_name) LIKE LOWER(?)";
        return getSoloResultBeans(genre, sql, name);
    }

    private List<SoloResultBean> getSoloResultBeans(String genre, String sql, String name) {
        ResultSet resultSet;
        List<SoloResultBean> soloResultBeans = new ArrayList<>();
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, genre);
            if (name!=null) preparedStatement.setString(2, name+"%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String encodedImg = resultSet.getString("encoded_profile_img");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                List<String> genreList;
                String [] temp = (String []) resultSet.getArray("genre").getArray();
                genreList = Arrays.asList(temp);
                SoloResultBean soloResultBean = new SoloResultBean(id, email, encodedImg, firstName, lastName);
                soloResultBean.setGenres(genreList);
                soloResultBeans.add(soloResultBean);
            }
            return soloResultBeans;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RepositoryException("Error advanced searching solo in DB");
        }
    }

    @Override
    public void run() {
        if (this.searchString == null && !advancedFiltersAllNull(advancedFilters)) {
            results.addAll(this.advancedSearchWithNoName(advancedFilters[0]));
        } else if (this.searchString != null && !advancedFiltersAllNull(advancedFilters)){
            results.addAll(this.advancedSearchWithName(this.searchString, advancedFilters[0]));
        } else if (advancedFiltersAllNull(advancedFilters) && this.searchString == null){
            throw new InputException("Type something to search or select one of the advanced filters");
        } else {
            results.addAll(this.searchByName(this.searchString));
        }
    }

    private boolean advancedFiltersAllNull(String[] advancedFilters) {
        for (int i = 0; i < 3; i++) {
            if (!advancedFilters[i].equals("")) return false;
        }
        return true;
    }

    public List<SoloResultBean> getResults() {
        return results;
    }
}
