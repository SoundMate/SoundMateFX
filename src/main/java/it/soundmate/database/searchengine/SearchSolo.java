/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:08
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.model.Solo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchSolo implements SearchEngine<SoloResultBean>, Runnable {

    private final String searchString;
    private final Connection connection;
    private final List<SoloResultBean> results = new ArrayList<>();

    public SearchSolo(String searchString, Connection connection){
        this.searchString = searchString;
        this.connection = connection;
    }

    /**
     * To be implemented better
     * Use a get Method from the soloDao to get all the Solo info
     * or a get Method specific for the search (avoiding unnecessary data like password)
     * like below
     *
     * @return*/
    @Override
    public List<SoloResultBean> searchForName(String name) {
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

    @Override
    public void run() {
       results.addAll(this.searchForName(this.searchString));
    }

    public List<SoloResultBean> getResults() {
        return results;
    }
}
