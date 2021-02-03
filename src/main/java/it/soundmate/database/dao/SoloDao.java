/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 21:10
 */

package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Genre;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoloDao implements Dao<Solo>{


    private final UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(SoloDao.class);
    private final Connector connector = Connector.getInstance();
    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_MESSAGE = "Error, check stack trace for details";

    public SoloDao(UserDao userDao) {
        this.userDao = userDao;
    }

    String sqlSolo = " WITH ins1 AS (\\n\" +\n" +
            "                    \"     INSERT INTO registered_users (email, password, user_type, city)\\n\" +\n" +
            "                    \"         VALUES (?, ?, ?, ?)\\n\" +\n" +
            "                    \" -- ON     CONFLICT DO NOTHING         -- optional addition in Postgres 9.5+\\n\" +\n" +
            "                    \"         RETURNING id AS sample_id\\n\" +\n" +
            "                    \" ), ins2 AS (\\n\" +\n" +
            "                    \"     INSERT INTO users (id)\\n\" +\n" +
            "                    \"         SELECT sample_id FROM ins1\\n\" +\n" +
            "                    \" )\\n\" +\n" +
            "                    \"INSERT INTO solo (id,first_name, last_name)\\n\" +\n" +
            "                    \"SELECT sample_id, ?, ? FROM ins1;";


    public int registerSolo(RegisterSoloBean soloBean){
        if (userDao.checkIfBanned(soloBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            throw new UpdateException("Account has been banned");
        } else if (userDao.checkEmailBoolean(soloBean.getEmail())){
            log.error(EMAIL_EXISTS_ERR);
            throw new DuplicatedEmailException("Duplicated email "+soloBean.getEmail());
        } else {
            String sql = "INSERT INTO solo (id, first_name, last_name) VALUES (?, ?, ?)";
            ResultSet resultSet;
            int id = userDao.register(soloBean);
            try (PreparedStatement prepStmt = connector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                prepStmt.setInt(1, id);
                prepStmt.setString(2, soloBean.getFirstName());
                prepStmt.setString(3, soloBean.getLastName());
                int rowAffected = prepStmt.executeUpdate();
                if (rowAffected == 1) {
                    resultSet = prepStmt.getGeneratedKeys();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    } else throw new RepositoryException("Error registering solo user");
                } else throw new RepositoryException("Error registering solo user (ROW AFFECTED != 1");
            } catch (SQLException ex) {
                throw new RepositoryException(ex.getMessage(), ex);
            }
        }
    }

    private void createGenresEntry(int userID) {
        String sql = "insert into fav_genres (id) values (?)";
        try (PreparedStatement preparedStatement = this.connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new RepositoryException("Error creating genres entry");
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

    public SoloResultBean getFullName(int id){
        String sql = "SELECT first_name, last_name FROM solo WHERE id = ?";
        SoloResultBean soloResultBean = new SoloResultBean();

        try(Connection conn = connector.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                soloResultBean.setFirstName(resultSet.getString("first_name"));
                soloResultBean.setLastName(resultSet.getString("last_name"));
            } return soloResultBean;
    }
         catch (SQLException ex) {
            throw new RepositoryException("Error fetching data. The error was: \n" + ex.getMessage(), ex);
        }
    }


    public Solo getSoloByID(int id) {
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

    public List<String> getInstruments(int id){
        String sql = "SELECT instruments FROM played_instruments WHERE id = ?";
        List<String> instrumentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getArray("instruments") == null) return instrumentList;
                String [] temp = (String []) resultSet.getArray("instruments").getArray();
                instrumentList = Arrays.asList(temp);
            }
            return instrumentList;
        } catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }
    }

    public boolean updateGenre(Solo solo, Genre genre){
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
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
            return userDao.getGenreList(id, genres, preparedStatement);
        } catch (SQLException ex){
            throw new RepositoryException(ERR_MESSAGE, ex);
        }
    }

    @Override
    public int register(RegisterBean registerBean) {
        try {
            int id = this.registerSolo((RegisterSoloBean) registerBean);
            this.createGenresEntry(id);
            this.createInstrumentsEntry(id);
            return id;
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    private void createInstrumentsEntry(int id) {
        String sql = "INSERT INTO played_instruments (id) VALUES (?)";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException("Error creating instruments entry: SQLException: "+e.getMessage(), e);
        }
    }

    @Override
    public int update(Solo solo) {
        return 0;
    }

    @Override
    public int delete(Solo solo) {
        return 0;
    }

    @Override
    public Solo get(int id) {
        return null;
    }
}

