package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DBException;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.RoomRenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class RoomRenterDao {

    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";
    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(RoomRenterDao.class);
    private final UserDao userDao;

    public RoomRenterDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int registerRoomRenter(RegisterRenterBean rentBean){
        ResultSet resultSet;
        if (userDao.checkIfBanned(rentBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            throw new DBException("Account banned");
        }else if (userDao.checkEmailBoolean(rentBean.getEmail())){
            log.error(EMAIL_EXISTS_ERR);
            throw new DuplicatedEmailException("Duplicated email "+rentBean.getEmail());
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
                    "INSERT INTO room_renter (id, name, address)\n" +
                    "SELECT sample_id, ?, ? FROM ins1;";

            try (Connection conn = connector.getConnection();
                 PreparedStatement preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                preparedStmt.setString(1, rentBean.getEmail());
                preparedStmt.setString(2, rentBean.getPassword());
                preparedStmt.setString(3, rentBean.getUserType().toString());
                preparedStmt.setString(4, rentBean.getCity());
                preparedStmt.setString(5, rentBean.getName());
                preparedStmt.setString(6, rentBean.getAddress());

                int rowAffected = preparedStmt.executeUpdate();
                if (rowAffected == 1) {
                    resultSet = preparedStmt.getGeneratedKeys();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                    else throw new RepositoryException(ERR_INSERT + "Result Set");
                } else throw new RepositoryException(ERR_INSERT + "Row affected != 1");
            } catch (SQLException ex) {
                throw new RepositoryException(ERR_INSERT + ex.getMessage(), ex);
            }
        }
    }

    public RoomRenter getRenterByID(int id) {
        RoomRenter roomRenter = new RoomRenter();
        ResultSet resultSet;
        String query = "SELECT users.id, email, password, encoded_profile_img, name, city, address\n" +
                " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
                " INNER JOIN room_renter rr on users.id = rr.id WHERE registered_users.id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection()
                .prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                roomRenter.setId(resultSet.getInt("id"));
                roomRenter.setEmail(resultSet.getString("email"));
                roomRenter.setPassword(resultSet.getString("password"));
                roomRenter.setEncodedImg(resultSet.getString("encoded_profile_img"));
                roomRenter.setName(resultSet.getString("name"));
                roomRenter.setCity(resultSet.getString("city"));
                roomRenter.setAddress(resultSet.getString("address"));
            return roomRenter;

            } else {
                throw new RepositoryException("Error ResultSet in getRenterByID");
            }
        } catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }
    }

    public void updateName(String name, RoomRenter roomRenter) {
        String sql = "UPDATE room_renter SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, roomRenter.getId());
            if (preparedStatement.executeUpdate() == 1) {
                roomRenter.setName(name);
            }
        } catch (SQLException sqlException) {
            throw new UpdateException("Error updating name, SQLException: "+sqlException.getMessage());
        }
    }

    public void updateAddress(String address, RoomRenter roomRenter) {
        String sql = "UPDATE room_renter SET address = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, roomRenter.getId());
            if (preparedStatement.executeUpdate() == 1) {
                roomRenter.setAddress(address);
            } else throw new UpdateException("Error updating address");
        } catch (SQLException sqlException) {
            throw new UpdateException("Error updating address, SQLException: "+sqlException.getMessage());
        }
    }
}
