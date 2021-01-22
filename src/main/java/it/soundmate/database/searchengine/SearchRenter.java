/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:09
 * Last edited: 09/01/21, 20:09
 */

package it.soundmate.database.searchengine;

import it.soundmate.bean.searchbeans.RoomRenterResultBean;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchRenter implements SearchEngine<RoomRenterResultBean>, Runnable {

    private final List<RoomRenterResultBean> results = new ArrayList<>();
    private final String searchString;
    private final Connection connection;
    private final String city;

    public SearchRenter(String searchString, Connection connection, String city){
        this.searchString = searchString;
        this.connection = connection;
        this.city = city;
    }

    @Override
    public List<RoomRenterResultBean> searchByName(String name) {
        String sql = "SELECT users.id, email, encoded_profile_img, name FROM users JOIN room_renter rr on users.id = rr.id JOIN registered_users ru on users.id = ru.id WHERE LOWER(name) LIKE LOWER(?)";
        ResultSet resultSet;
        List<RoomRenterResultBean> roomRenterResultBeanList = new ArrayList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name+"%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String encodedImg = resultSet.getString("encoded_profile_img");
                String renterName = resultSet.getString("name");
                RoomRenterResultBean resultBean = new RoomRenterResultBean(id, email, encodedImg, renterName);
                roomRenterResultBeanList.add(resultBean);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return roomRenterResultBeanList;
    }


    @Override
    public void run() {
        this.results.addAll(this.searchByName(this.searchString));
    }

    public List<RoomRenterResultBean> getResults() {
        return results;
    }
}
