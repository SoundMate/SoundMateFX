package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.RoomRenter;
import it.soundmate.model.Solo;
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

    public int regiRoomRenterController(RegisterRenterBean rentBean){

        ResultSet resultSet;

        int userID = 0;
        if (userDao.checkIfBanned(rentBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            return -1;
        }else if (userDao.checkEmailBoolean(rentBean.getEmail())){
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
                    "INSERT INTO room_manager (id, first_name, last_name)\n" +
                    "SELECT sample_id, ?, ? FROM ins1;";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, rentBean.getEmail());
                pstmt.setString(2, rentBean.getPassword());
                pstmt.setString(3, rentBean.getUserType().toString());
                pstmt.setString(4, "profileImg");
                pstmt.setString(5, rentBean.getFirstName());
                pstmt.setString(6, rentBean.getLastName());

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

    public RoomRenter getRenterByID(int id) {
        ResultSet resultSet;
        RoomRenter roomRenter = new RoomRenter();
        String query = "SELECT email, password, encoded_profile_img, age, first_name, last_name\n" +
                " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
                " INNER JOIN solo ON (registered_users.id = solo.id) WHERE registered_users.id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection()
                .prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                roomRenter.setId(id);
                roomRenter.setEmail(resultSet.getString("email"));
                roomRenter.setPassword(resultSet.getString("password"));
                roomRenter.setEncodedImg(resultSet.getString("encoded_profile_img"));
                roomRenter.setFirstName(resultSet.getString("first_name"));
                roomRenter.setLastName(resultSet.getString("last_name"));
            }
        }catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }return roomRenter;
    }


}
