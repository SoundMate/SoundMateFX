package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Band;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BandDao {


    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";
    private final UserDao userDao;
    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(BandDao.class);

    public BandDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int registerBand(RegisterBandBean bandBean){
        ResultSet resultSet;
        if (userDao.checkIfBanned(bandBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            throw new RepositoryException("Account has been banned");
        }else if (userDao.checkEmailBoolean(bandBean.getEmail())){
            log.error(EMAIL_EXISTS_ERR);
            throw new DuplicatedEmailException("Duplicated email "+bandBean.getEmail());
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
                    "INSERT INTO band (id, band_name)\n" +
                    "SELECT sample_id, ? FROM ins1;";

            try (Connection conn = connector.getConnection();
                 PreparedStatement preparedStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                preparedStmt.setString(1, bandBean.getEmail());
                preparedStmt.setString(2, bandBean.getPassword());
                preparedStmt.setString(3, bandBean.getUserType().toString());
                preparedStmt.setString(4, bandBean.getCity());
                preparedStmt.setString(5, bandBean.getBandName());

                int rowAffected = preparedStmt.executeUpdate();
                if (rowAffected == 1) {
                    resultSet = preparedStmt.getGeneratedKeys();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    } else throw new RepositoryException("Unable to register new User");
                } else throw new RepositoryException("Unable to register new User, RowAffected != 1");

            } catch (SQLException ex) {
                throw new RepositoryException(ERR_INSERT, ex);
            }
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
