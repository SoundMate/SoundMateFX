package it.soundmate.database.dao;

import it.soundmate.bean.registerbeans.RegisterBandBean;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.Band;
import it.soundmate.model.Genre;
import it.soundmate.view.uicomponents.SocialLinks;
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

    private void createGenreEntry(int userID) {
        String sql = "insert into played_genres (id) values (?)";
        try (PreparedStatement preparedStatement = this.connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            log.error("Error creating genre entry");
            sqlException.printStackTrace();
        }
    }


    public Band getBandByID(int id) {
        ResultSet resultSet;
        Band bandUser = new Band();
        String query = "SELECT email, password, encoded_profile_img, band_name, city\n" +
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
                bandUser.setCity(resultSet.getString("city"));

            }
        }catch (SQLException exc) {
            throw new RepositoryException("Err Fetching User", exc);
        }return bandUser;
    }

    public List<Genre> getGenres(int id) {
        String sql = "SELECT genre FROM played_genres WHERE id = ?";
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
            return userDao.getGenreList(id, genres, preparedStatement);
        } catch (SQLException ex){
            throw new RepositoryException("Error fetching genres for band, SQL Exception: "+ex.getMessage(), ex);
        }
    }

    public void updateName(String name, Band band) {
        String sql = "UPDATE band SET band_name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
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
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)){
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

    public void updateSocialLink(String name, int position, Band band) {
        String sql;
        switch (position) {
            case 0:
                sql = "UPDATE band set spotify = (?) where id = (?)";
                break;
            case 1:
                sql = "UPDATE band set youtube = (?) where id = (?)";
                break;
            case 2:
                sql = "UPDATE band set facebook = (?) where id = (?)";
                break;
            default:
                throw new InputException("Not valid position link");
        }
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, band.getId());
                if (preparedStatement.executeUpdate() == 1) {
                    band.setSocialLinks(this.getSocialLinks(band.getId()));
                }
        } catch (SQLException e) {
            throw new RepositoryException("Unable to update social link");
        }
    }

    public SocialLinks[] getSocialLinks(int id) {
        SocialLinks[] socialLinks = new SocialLinks[3];
        String sql = "SELECT spotify, youtube, facebook FROM band WHERE id = (?)";
        try (PreparedStatement preparedStatement = connector.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String spotifyLink = resultSet.getString("spotify");
                String youtubeLink = resultSet.getString("youtube");
                String facebookLink = resultSet.getString("facebook");
                socialLinks[0] = SocialLinks.SPOTIFY;
                socialLinks[0].setLink(spotifyLink);
                socialLinks[1] = SocialLinks.YOUTUBE;
                socialLinks[1].setLink(youtubeLink);
                socialLinks[2] = SocialLinks.FACEBOOK;
                socialLinks[2].setLink(facebookLink);
                return socialLinks;
            } else throw new RepositoryException("Unable to fetch social links");
        } catch (SQLException e) {
            throw new RepositoryException("Unable to update social link");
        }
    }
}
