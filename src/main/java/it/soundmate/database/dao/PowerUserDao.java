package it.soundmate.database.dao;

import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PowerUserDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(PowerUserDao.class);
    private static final String EMAIL = "Email";
    private static final String UNBAN_STAT = "DELETE FROM banned_users WHERE Email = ?";
    private static final String USR_NOT_FOUND = "\n\t ***** USER NOT FOUND *****\n";
    private static final String BANNED = "\n\t ***** USER HAS BEEN BANNED ***** ";
    private static final String SUCCESS = "Entry successfully modified! ";


    public boolean banUser(String email){
        int deletedRec;
        String sql = "WITH del AS (DELETE FROM registered_users WHERE email = ? RETURNING email)\n" +
                "    INSERT INTO banned_users (email) SELECT * FROM del;";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            deletedRec = stmt.executeUpdate();

            if (deletedRec == 1) {
                log.info(BANNED);
                return true;
            }

            else log.info(USR_NOT_FOUND);

        } catch (SQLException ex) {
            log.error(ex.getMessage());
        } return false;
    }


    public boolean unbanEmail(String email){
        int deletedRec;

        try(Connection conn = connector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(UNBAN_STAT)){

            pstmt.setString(1, email);
            deletedRec = pstmt.executeUpdate();

            if (deletedRec == 1){
                log.info(SUCCESS);
                return true;

            }
            else log.info(USR_NOT_FOUND);
        } catch (SQLException ex){
            log.error((ex.getMessage()));
        } return false;
    }


    public List<String> getAllBanned() {
        List<String> banned = new ArrayList<>();

        String sql = "SELECT * FROM banned_users";

        try (Connection conn = connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {

                String email = resultSet.getString(EMAIL);
                banned.add(email);
            }
            return banned;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error Fetching Users", ex);
        }
    }

    public void printBannedInTab(List<String> banned){
        if (banned.isEmpty()){
            log.info("\n\t ***** NO BANNED USERS *****\n");
        }
        else {
            log.info("\n" + "Banned Users Email:");
            for (String str : banned) {
                log.info("{}", str);
            }
        }
    }

    public void unbanAll(){
        String sql = "DELETE FROM banned_users";
        int delRecs;
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** All Users Unbanned! *****");
            else log.info("\t ***** No entries found! *****");

        } catch (SQLException ex) {
            throw new RepositoryException("Error Unbanning All", ex);
        }

    }

    //testing purpose
    public void delete(String query, String resetCodeQuery) {
        int delRecs;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** Application Entries Successfully Cleaned! *****");
            resetCode(resetCodeQuery);

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting Applications", ex);
        }
    }

    private void resetCode(String query) {
        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.executeUpdate();
            log.info("\t ***** Code Values reset successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error ResetCode", ex);
        }
    }

}
