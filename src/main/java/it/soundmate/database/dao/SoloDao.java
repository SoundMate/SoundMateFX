/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 21:10
 */

package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class SoloDao {


    private UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(SoloDao.class);
    private final Connector connector;
    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";


    public SoloDao(Connector connector, UserDao userDao) {
        this.connector = connector;
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
                    "     INSERT INTO registered_users (email, password, user_type)\n" +
                    "         VALUES (?, ?, ?)\n" +
                    " -- ON     CONFLICT DO NOTHING         -- optional addition in Postgres 9.5+\n" +
                    "         RETURNING id AS sample_id\n" +
                    " ), ins2 AS (\n" +
                    "     INSERT INTO users (id, encoded_profile_img)\n" +
                    "         SELECT sample_id, ? FROM ins1\n" +
                    " )\n" +
                    "INSERT INTO solo (id,first_name, last_name)\n" +
                    "SELECT sample_id, ?, ? FROM ins1;";

            try (PreparedStatement pstmt = connector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, soloBean.getEmail());
                pstmt.setString(2, soloBean.getPassword());
                pstmt.setString(3, soloBean.getUserType().toString());
                pstmt.setString(4, "blabla");
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

    public boolean updateFirstName(Solo solo, String firstName) {
        String query = "update solo set first_name = ? where id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, solo.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateLastName(Solo solo, String lastName) {
        String query = "update solo set last_name = ? where id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, solo.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }


    public Solo getSoloByID(int id) {
        ResultSet resultSet;
        Solo soloUser = new Solo();
        String query = "SELECT email, password, encoded_profile_img, age, first_name, last_name\n" +
        " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
        " INNER JOIN solo ON (registered_users.id = solo.id) WHERE registered_users.id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection()
                                                .prepareStatement(query)) {

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
            }
            return soloUser;
        }catch (SQLException exc) {
            throw new SQLException ("Fetch Error", exc);
        }
    }

}
