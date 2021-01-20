/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 21:10
 */

package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoloDao {


    private final UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(SoloDao.class);
    private final Connector connector = Connector.getInstance();
    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";
    private static final String ERR_MESSAGE = "Error, check stack trace for details";


    //Modificato il costruttore per il Connector (attributo come UserDao)
    public SoloDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int registerSolo(RegisterSoloBean soloBean){

        ResultSet resultSet;

        int userID = 0;
        if (userDao.checkIfBanned(soloBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            return -1;
        }else if (userDao.checkEmailBoolean(soloBean.getEmail())){
            log.error(EMAIL_EXISTS_ERR);
            return -2;
        } else {
            String sql = " WITH ins1 AS (\n" +
                    "     INSERT INTO registered_users (email, password, user_type, city)\n" +
                    "         VALUES (?, ?, ?, ?)\n" +
                    " -- ON     CONFLICT DO NOTHING         -- optional addition in Postgres 9.5+\n" +
                    "         RETURNING id AS sample_id\n" +
                    " ), ins2 AS (\n" +
                    "     INSERT INTO users (id)\n" +
                    "         SELECT sample_id FROM ins1\n" +
                    " )\n" +
                    "INSERT INTO solo (id,first_name, last_name)\n" +
                    "SELECT sample_id, ?, ? FROM ins1;";

            try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, soloBean.getEmail());
                pstmt.setString(2, soloBean.getPassword());
                pstmt.setString(3, soloBean.getUserType().toString());
                pstmt.setString(4, soloBean.getCity());
                pstmt.setString(5, soloBean.getFirstName());
                pstmt.setString(6, soloBean.getLastName());


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {

                    resultSet = pstmt.getGeneratedKeys();
                    if (resultSet.next())
                        userID = resultSet.getInt(1);
                }
            } catch (SQLException ex) {
                throw new RepositoryException(ERR_INSERT, ex);
            } return userID;
        }
    }

    public void updateFirstName(Solo solo, String firstName) {
        String query = "update solo set first_name = ? where id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, solo.getId());
            if (preparedStatement.executeUpdate() == 1) solo.setFirstName(firstName);
        } catch (SQLException e) {
            throw new UpdateException("Unable to update first name, SQLException: "+e.getMessage());
        }
    }

    public void updateLastName(Solo solo, String lastName) {
        String query = "update solo set last_name = ? where id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, solo.getId());
            if (preparedStatement.executeUpdate() == 1) solo.setLastName(lastName);
        } catch (SQLException e) {
            throw new UpdateException("Unable to update last name, SQLException: "+e.getMessage());
        }
    }


    public Solo getSoloByID(int id) throws SQLException {
        ResultSet resultSet;
        Solo soloUser = new Solo();
        String query = "SELECT email, password, encoded_profile_img, age, first_name, last_name, city\n" +
        " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
        " INNER JOIN solo ON (registered_users.id = solo.id) WHERE registered_users.id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                soloUser.setId(id);
                soloUser.setEmail(resultSet.getString("email"));
                soloUser.setPassword(resultSet.getString("password"));
                soloUser.setAge(resultSet.getInt("age"));
                soloUser.setEncodedImg(resultSet.getString("encoded_profile_img"));
                soloUser.setFirstName(resultSet.getString("first_name"));
                soloUser.setLastName(resultSet.getString("last_name"));
                soloUser.setCity(resultSet.getString("city"));
            }
            return soloUser;
        }catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }
    }


    public boolean insertInstruments(Solo solo, List<String> genres) {

        String sql = "INSERT INTO played_instruments (id, instruments) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){

            preparedStatement.setInt(1,solo.getId());
            preparedStatement.setArray(2, connector.getConnection().createArrayOf("text", genres.toArray()));

            if (preparedStatement.executeUpdate() == 1)
                return true;

        } catch (SQLException e){
            throw new RepositoryException("Error, check the stack trace for details", e);
        }return false;
    }


    public boolean updateInstrument(Solo solo, String instrument){

        //::text is the parsing for sql queries (the value in ? must be a text type)
        String sql = "UPDATE played_instruments SET instruments = array_append(instruments, ?::text) WHERE id = ?";



        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){

            preparedStatement.setString(1, instrument);
            preparedStatement.setInt(2, solo.getId());

            return preparedStatement.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }
    }

    public List<String> getInstruments(Solo solo){
        String sql = "SELECT instruments FROM played_instruments WHERE id = ?";
        List<String> instrumentList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){

            preparedStatement.setInt(1, solo.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                String [] temp = (String []) resultSet.getArray("instruments").getArray();
                instrumentList = Arrays.asList(temp);

            }
            return instrumentList;

        } catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }

    }



    public boolean insertGenres(Solo solo, List<Genre> genreList){
        String sql = "INSERT INTO fav_genres (id, genre) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){

            preparedStatement.setInt(1,solo.getId());
            preparedStatement.setArray(2, connector.getConnection().createArrayOf("text", genreList.toArray()));

            if (preparedStatement.executeUpdate() == 1) {
                solo.setFavGenres(genreList);
                return true;
            }

        } catch (SQLException e){
            throw new RepositoryException("Error, check the stack trace for details", e);
        }return false;
    }

    public boolean updateGenre(Solo solo, Genre genre){
        //::text is the parsing for sql queries (the value in ? must be a text type)
        String sql = "UPDATE fav_genres SET genre = array_append(genre, ?::text) WHERE id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
            preparedStatement.setString(1, genre.toString());
            preparedStatement.setInt(2, solo.getId());
            boolean result = preparedStatement.executeUpdate() == 1;
            if (result) solo.addGenre(genre);
            return result;
        } catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }
    }

    public List<Genre> getGenres(int id) {
        String sql = "SELECT genre FROM fav_genres WHERE id = ?";
        List<String> genreList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String [] temp = (String []) resultSet.getArray("genre").getArray();
                genreList = Arrays.asList(temp);
            }
            List<Genre> genres = new ArrayList<>();
            for (String genre : genreList) {
                genres.add(Genre.returnGenre(genre));
            }
            return genres;
        } catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }
    }
}

