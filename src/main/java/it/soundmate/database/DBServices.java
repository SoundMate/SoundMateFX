package it.soundmate.database;


import it.soundmate.utils.ImgBase64Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBServices {

    private static DBServices instance = null;
    private static final String SQL_IMG_UPDATE = "UPDATE users SET encoded_profile_img = ? WHERE id = ?";
    private static final Logger log = LoggerFactory.getLogger( DBServices.class );
    private static final String EMAIL = "email";
    private final Connector connector;

    public DBServices(Connector connector) {
        this.connector = connector;
    }

    public static DBServices getInstance() {
        if (instance == null) {
            instance = new DBServices(Connector.getInstance());
        }
        return instance;
    }

    public void deleteAll() {
        String sql = "DELETE FROM registered_users";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** user entries successfully cleaned! *****");
            resetID();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Delete All", ex);
        }
    }

    private void resetID() {
        String sql = "ALTER SEQUENCE registered_users_id_seq RESTART WITH 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** ID Values resetted successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error ResetID", ex);
        }
    }

    public boolean checkIfBanned(String mail) {
        String sql = "SELECT email FROM banned_users WHERE email = ? ";
        boolean res = false;
        ResultSet resultSet;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mail);
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String queriedMail = resultSet.getString(EMAIL);
                if (queriedMail.equals(mail)) {
                    res = true;
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Querying", ex);
        } return res;
    }


    public boolean checkEmailBoolean(String email) {
        String sql = "SELECT email FROM registered_users WHERE email = ?";
        ResultSet resultSet;
        boolean result = false;


        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String mail = resultSet.getString(EMAIL);
                if (mail.equals(email)) result = true;

            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Checking Email", ex);
        } return result;
    }



    public String getUserType(String email){
        String sql = "SELECT user_type FROM registered_users WHERE email = ?";
        ResultSet resultSet = null;
        String userType = "";

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStmt = connection.prepareStatement(sql)){

            preparedStmt.setString(1, email);

            resultSet = preparedStmt.executeQuery();

            while(resultSet.next()){
                userType = resultSet.getString("user_type");
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error Querying UserCode", ex);
        }
        return userType;
    }


    public int uploadHandler(int userId, Path pathToImage){
        try (Connection conn = connector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_IMG_UPDATE)) {

            pstmt.setString(1, ImgBase64Repo.encode(pathToImage));
            pstmt.setInt(2, userId);

            return pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Updating Image", ex);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}
