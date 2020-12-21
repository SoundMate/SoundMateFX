/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 11/12/20, 15:42
 */

package it.soundmate.database;

import it.soundmate.model.Band;
import it.soundmate.model.BandManager;
import it.soundmate.model.User;
import it.soundmate.model.UserType;

import java.io.InputStream;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao implements Dao<User> {

    /*Singleton*/

    private static UserDao instance = null;

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User getByID(long id) {
        return null;
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        String query = "select * from \"Users\" where email=? and password=?;";
        ResultSet result;
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery();
            User user = new User();
            while (result.next()) {
                user.setFirstName(result.getString("firstName"));
                user.setLastName(result.getString("lastName"));
                user.setEmail(result.getString("email"));
                user.setUserID(result.getInt("id"));
                InputStream profilePicBytes = result.getBinaryStream("profile_pic");
                if (profilePicBytes != null) {
                    user.setProfilePic(profilePicBytes);
                }
                switch (result.getInt("type")) {
                    case 1:
                        user.setUserType(UserType.SOLO);
                        break;
                    case 2:
                        user.setUserType(UserType.BAND_MANAGER);
                        break;
                    case 3:
                        user.setUserType(UserType.BAND_ROOM_MANAGER);
                        break;
                    default:
                        return null;
                }
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String email, String password, String firstName, String lastName, int type) {
        String query = "insert into \"Users\" (email, password, \"firstName\", \"lastName\", type) values (?,?,?,?,?);";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5,type);
            //executeUpdate returns the numbers of affected row
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean registerSoloFromUsers(int id) {
        String query = "insert into \"Solos\" (\"userID\") values (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            //executeUpdate returns the numbers of affected row
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean registerBand(String name) {
        String query = "insert into \"Bands\" (band_name) values (?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, name);
            //executeUpdate returns the numbers of affected row
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean registerBandManager(int id, String name) {
        String query = "insert into \"BandManagers\" (\"userID\", bands) values (?,?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            String[] nameArray = {name};
            Array bandNameArray = Connector.getInstance().getConnection().createArrayOf("text", nameArray);
            preparedStatement.setInt(1, id);
            preparedStatement.setArray(2, bandNameArray);
            //executeUpdate returns the numbers of affected row
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean registerBandRoomManager(int id, String name) {
        String query = "insert into \"BandRoomManager\" (\"userid\", bandroomname) values (?,?)";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            //executeUpdate returns the numbers of affected row
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public boolean saveProfilePicForUser(InputStream inputStream, int userID) {
        String query;
        query = "update \"Users\" set profile_pic = (?) where id = (?);";
        try (PreparedStatement preparedStatement = Connector.getInstance().getConnection().prepareStatement(query)){
            preparedStatement.setBinaryStream(1, inputStream);
            preparedStatement.setInt(2, userID);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
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
}
