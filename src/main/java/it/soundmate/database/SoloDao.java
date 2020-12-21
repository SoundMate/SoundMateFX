/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 17/12/20, 11:44
 */

package it.soundmate.database;

import it.soundmate.model.Solo;
import it.soundmate.model.User;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SoloDao implements Dao<Solo> {

    /*Singleton*/

    private static SoloDao instance = null;
    private UserDao userDao = UserDao.getInstance();

    public static SoloDao getInstance() {
        if (instance == null) {
            instance = new SoloDao();
        }
        return instance;
    }

    @Override
    public Solo getByID(long id) {
        return null;
    }

    @Override
    public Solo getByEmailAndPassword(String email, String password) {
        return null;
    }

    public Solo getSoloByUser(User user) {
        Solo soloUser = new Solo(user);
        String query;
        query = "select * from \"Solos\" where \"userID\" = (?);";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Array favGenres = resultSet.getArray("favGenres");
                Array instruments = resultSet.getArray("instruments");
                Array bands = resultSet.getArray("bands");
                if (favGenres != null) {
                    String[] favGenresArray = (String[]) favGenres.getArray();
                    List<String> favGenresList = Arrays.asList(favGenresArray);
                    soloUser.setFavouriteGenres(favGenresList);
                }
                if (instruments != null) soloUser.setInstrument(Arrays.asList((String[]) instruments.getArray()));
                if (bands != null) {
                    Integer[] bandIDs = (Integer[]) bands.getArray();
                    for (Integer bandID : bandIDs) {
                        soloUser.joinBand(userDao.getBandByID(bandID));
                    }
                }
            }
            return soloUser;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public boolean addGenreToSolo(String genre, Solo soloUser) {
        String query = "update \"Solos\" set \"favGenres\"[(?)] = (?) where \"userID\" = (?);";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, soloUser.getFavouriteGenres().size() - 1);
            preparedStatement.setString(2, genre);
            preparedStatement.setInt(3, soloUser.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }
}
