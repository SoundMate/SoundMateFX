package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BandDao implements Dao<Band>{


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
                        this.createGenreEntry(resultSet.getInt(1));
                        return resultSet.getInt(1);
                    } else throw new RepositoryException("Unable to register new User");
                } else throw new RepositoryException("Unable to register new User, RowAffected != 1");

            } catch (SQLException ex) {
                throw new RepositoryException(ERR_INSERT, ex);
            }
        }
    }

    public BandResultBean getBandName(int id){
        String sql = "SELECT band_name FROM band WHERE id = ?";
        BandResultBean bandResultBean = new BandResultBean();
        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                bandResultBean.setBandName(resultSet.getString("band_name"));
            } return bandResultBean;
        }
        catch (SQLException ex) {
            throw new RepositoryException("Error fetching data. The error was: \n" + ex.getMessage(), ex);
        }
    }

    private void createGenreEntry(int userID) {
        String sql = "insert into played_genres (id) values (?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            log.error("Error creating genre entry");
            throw new RepositoryException("Error creating genre entry", sqlException);
        }
    }


    public Band getBandByID(int id) {
        ResultSet resultSet;
        Band bandUser = new Band();
        String query = "SELECT email, password, encoded_profile_img, band_name, city\n" +
                " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
                " INNER JOIN band ON (registered_users.id = band.id) WHERE registered_users.id = ?";

        try (Connection conn = connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                bandUser.setId(id);
                bandUser.setEmail(resultSet.getString("email"));
                bandUser.setPassword(resultSet.getString("password"));
                bandUser.setEncodedImg(resultSet.getString("encoded_profile_img"));
                bandUser.setBandName(resultSet.getString("band_name"));
                bandUser.setCity(resultSet.getString("city"));

            }
        }catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }return bandUser;
    }

    public List<Genre> getGenres(int id) {
        String sql = "SELECT genre FROM played_genres WHERE id = ?";
        List<Genre> genres = new ArrayList<>();
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            return userDao.getGenreList(id, genres, preparedStatement);
        } catch (SQLException ex){
            throw new RepositoryException("Error fetching genres for band, SQL Exception: "+ex.getMessage(), ex);
        }
    }

    public void updateName(String name, Band band) {
        String sql = "UPDATE band SET band_name = ? WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, band.getId());
            if (preparedStatement.executeUpdate() == 1) {
                band.setBandName(name);
            }
        } catch (SQLException sqlException) {
            throw new UpdateException("Error updating name, SQLException: "+sqlException.getMessage());
        }
    }

    public boolean updateGenre(Band band, Genre genre) {
        String sql = "UPDATE played_genres SET genre = array_append(genre, ?::text) WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setString(1, genre.name());
            preparedStatement.setInt(2, band.getId());
            boolean result = preparedStatement.executeUpdate() == 1;
            if (result) band.addGenre(genre);
            return result;
        } catch (SQLException ex){
            throw new RepositoryException("Error inserting genre, SQL Exception: "+ex.getMessage(), ex);
        }
    }

    @Override
    public int register(RegisterBean registerBean) {
        try {
            return this.registerBand((RegisterBandBean) registerBean);
        } catch (RepositoryException repositoryException) {
            throw new RepositoryException(repositoryException.getMessage());
        }
    }

    @Override
    public int update(Band band) {
        return 0;
    }

    @Override
    public int delete(Band band) {
        return 0;
    }

    @Override
    public Band get(int id) {
        return null;
    }

}
