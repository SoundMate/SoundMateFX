/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 08/01/21, 21:51
 * Last edited: 08/01/21, 20:19
 */

package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.DBServices;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.database.dbexceptions.BannedAccountException;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao implements Dao<User> {

    /*Singleton*/

    private final Connector connector = Connector.getInstance();
    private final DBServices dbServices = DBServices.getInstance();
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private static UserDao instance = null;
    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public int registerSolo(RegisterSoloBean registerSoloBean) {
        log.info("Registering Solo In DATABASE");
        ResultSet resultSet;
        int soloID = 0;

        if (dbServices.checkIfBanned(registerSoloBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            throw new BannedAccountException(ACC_BANNED_ERR);
        } else if (dbServices.checkEmailBoolean(registerSoloBean.getEmail())){
            log.error(EMAIL_EXISTS_ERR);
            throw new DuplicatedEmailException(EMAIL_EXISTS_ERR);
        } else {

            String sql = "WITH ins1 AS (\n" + "INSERT INTO registered_users (email, password, user_type)\n" +
                    "VALUES (?, ?, ?)\n"+" -- ON CONFLICT DO NOTHING -- optional addition in Postgres 9.5+\n" +
                    "RETURNING id AS sample_id\n"+"), ins2 AS (\n"+"INSERT INTO users (id)\n" +
                    "SELECT sample_id FROM ins1\n"+")\n"+"INSERT INTO solo (id, first_name, last_name)\n" +
                    "SELECT sample_id, ?, ? FROM ins1;";

            try (Connection conn = connector.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, registerSoloBean.getEmail());
                preparedStatement.setString(2, registerSoloBean.getPassword());
                preparedStatement.setString(3, registerSoloBean.getUserType().toString());
                preparedStatement.setString(4, registerSoloBean.getFirstName());
                preparedStatement.setString(5, registerSoloBean.getLastName());


                int rowAffected = preparedStatement.executeUpdate();
                if (rowAffected == 1) {
                    resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next())
                        return resultSet.getInt(1);
                }
            } catch (SQLException ex) {
                throw new RepositoryException(ERR_INSERT, ex);
            }
        }
        return soloID; //If soloID == 0 --> Error ??
    }

    public User login(String email, String password){
        log.info("Logging in User {}", email);
        ResultSet resultSet;
        String sql = "SELECT id ,user_type FROM registered_users WHERE email = ? AND password = ?;";
        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String userType = resultSet.getString("user_type");
                log.info("Logging in with user type {}", userType);
                if (userType.equals(UserType.SOLO.toString())) {
                    return this.loginSolo(resultSet.getInt("id"));
                }
            }
        } catch (SQLException sqlException){
            throw new RepositoryException("Error: DB not responding! \n Check stacktrace for details");
        }
        return null; //Error
    }

    public Solo loginSolo(int id) {
        log.info("Logging in Solo User ID: {}", id);
        ResultSet resultSet;
        String sql = "SELECT solo.id, email, password, first_name, last_name, encoded_profile_img FROM registered_users " +
                "JOIN solo ON solo.id = registered_users.id JOIN users u on registered_users.id = u.id WHERE solo.id=?;";
        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String encodedImg = resultSet.getString("encoded_profile_img");
                User user = new User(id, email, password, encodedImg, UserType.SOLO);
                log.info("Returning Solo User {} {}", resultSet.getString("first_name"), resultSet.getString("last_name"));
                return new Solo(user, resultSet.getString("first_name"), resultSet.getString("last_name"));
            }

        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            throw new RepositoryException("Error: DB not responding! \n Check stacktrace for details");
        }
        return null; //Error
    }


    public Band getBandByID(Integer bandID) {
        Band band = new Band(bandID);
        String query = "select * from \"Bands\" where \"bandID\" = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1,bandID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                band.setBandName(resultSet.getString("band_name"));
                band.setGenres(Arrays.asList((String []) resultSet.getArray("genres").getArray()));
            }
            return band;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updatePassword(User user, String password) {
        String query = "update \"Users\" set password = (?) where id = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, user.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateFirstName(User user, String firstName) {
        String query = "update \"Users\" set \"firstName\" = (?) where id = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setInt(2, user.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateLastName(User user, String lastName) {
        String query = "update \"Users\" set \"lastName\" = (?) where id = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, lastName);
            preparedStatement.setInt(2, user.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateEmail(User user, String email) {
        String query = "update \"Users\" set email = (?) where id = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, user.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteUser(User user){
        String query = "delete from \"Users\" where id = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserID());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public BandManager getBandManagerFromUser(User user) {
        BandManager bandManager = null;
        String query = "select * from \"BandManagers\" where \"userID\" = (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, user.getUserID());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<Integer> bandIDs = Arrays.asList((Integer[]) resultSet.getArray("bands").getArray());
                if (!bandIDs.isEmpty()) {
                    List<Band> bandList = new ArrayList<>();
                    for (Integer bandID : bandIDs) {
                        bandList.add(this.getBandByID(bandID));
                    }
                    bandManager = new BandManager(user, bandList);
                } else {
                    bandManager = new BandManager(user);
                }
            }
            return bandManager;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int register(RegisterBean registerBean) {
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public User get(int id) {
        return null;
    }
}
