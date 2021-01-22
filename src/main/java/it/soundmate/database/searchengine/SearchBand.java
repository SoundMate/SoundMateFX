/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.BandResultBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<BandResultBean> searchByName(String name) {
        String sql = "SELECT users.id, email, encoded_profile_img, band_name FROM users JOIN band ON users.id = band.id JOIN registered_users ru on users.id = ru.id WHERE LOWER(band_name) LIKE LOWER(?)";
        ResultSet resultSet;
        List<BandResultBean> bandResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+"%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String encodedImg = resultSet.getString("encoded_profile_img");
                String bandName = resultSet.getString("band_name");
                BandResultBean resultBean = new BandResultBean(id, email, encodedImg, bandName);
                bandResultBeanList.add(resultBean);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return bandResultBeanList;
    }


    @Override
    public void run() {
       this.results.addAll(this.searchByName(this.searchString));
    }

    public List<BandResultBean> getResults() {
        return results;
    }
}
