package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Band;
import it.soundmate.model.Solo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BandDao {


    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";
    private final UserDao userDao;
    private final Connector connector;
    private static final Logger log = LoggerFactory.getLogger(BandDao.class);

//    private final UserDao


    public BandDao(Connector connector, UserDao userDao) {
        this.connector = connector;
        this.userDao = userDao;
    }

    public int regiBandController(RegisterBandBean bandBean){

        ResultSet resultSet;

        int userID = 0;
        if (userDao.checkIfBanned(bandBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            return -1;
        }else if (userDao.checkEmailBoolean(bandBean.getEmail())){
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
                    "INSERT INTO band (id, band_name)\n" +
                    "SELECT sample_id, ? FROM ins1;";

            try (Connection conn = connector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstmt.setString(1, bandBean.getEmail());
                pstmt.setString(2, bandBean.getPassword());
                pstmt.setString(3, bandBean.getUserType().toString());
                pstmt.setString(4, "qui c'è un'img cod.");
                pstmt.setString(5, bandBean.getBandName());

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


    public Band getBandByID(int id) {
        ResultSet resultSet;
        Band bandUser = new Band();
        String query = "SELECT email, password, encoded_profile_img, band_name\n" +
                " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
                " INNER JOIN band ON (registered_users.id = band.id) WHERE registered_users.id = ?";

        try (PreparedStatement preparedStatement = connector.getConnection()
                .prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                bandUser.setId(id);
                bandUser.setEmail(resultSet.getString("email"));
                bandUser.setPassword(resultSet.getString("password"));
                bandUser.setEncodedImg(resultSet.getString("encoded_profile_img"));
                bandUser.setBandName(resultSet.getString("band_name"));

            }
        }catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }return bandUser;
    }




}
