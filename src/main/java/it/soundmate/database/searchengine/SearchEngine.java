/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 09/01/21, 20:08
 * Last edited: 09/01/21, 20:05
 */

package it.soundmate.database.searchengine;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SearchEngine<T> {

    default void prepareStatementWithGenre(String name, String city, String genre, PreparedStatement preparedStatement) throws SQLException {
        prepareStatementGeneric(name, city, preparedStatement);
        if (genre == null || genre.equals("NONE")) preparedStatement.setString(3, "%");
        else preparedStatement.setString(3, genre+"%");
    }

    default void prepareStatementGeneric(String name, String city, PreparedStatement preparedStatement) throws SQLException {
        if (name == null || name.equals("")) preparedStatement.setString(1,"%");
        else preparedStatement.setString(1, name+"%");
        if (city == null || city.equals("")) preparedStatement.setString(2, "%");
        else preparedStatement.setString(2, city+"%");
    }

    List<T> searchByNameAndCity(String name, String city);
}
