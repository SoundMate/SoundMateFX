package it.soundmate.database.dao;

import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Application;
import it.soundmate.model.Band;
import org.postgresql.jdbc.PgArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ApplicationDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(ApplicationDao.class);
    private static final String SUCCESS = "Entry successfully modified!";
    private static final String FAILED = "ERR: Operation Failed!";
    private static final String ERROR = "Error updating entry. The error was: \n";

    //crud
    //the band post an application
    public Application createApplication(Application application) {
        String sql = "insert into applications (id_band, message, instruments) values (?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setInt(1, application.getBandId());
            preparedStatement.setString(2, application.getMessage());
            preparedStatement.setArray(3, conn.createArrayOf("text", application.getInstrumentsList().toArray()));

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return application.withCode(resultSet.getInt("code"));
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error inserting entry: the error was: \n" + ex.getMessage(), ex);
        } return application.withCode(-1); //invalid code, abort (must be > 0)
    }

    //ritorna tutte le application "aperte" da una band (se una, ritorna una lista di un elemento)
    public List<Application> getApplicationByBandId(int bandId) {
        String sql = "SELECT code, solos_id, message, instruments FROM applications where id_band = ?";
        List<Application> applicationsList = new ArrayList<>();

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, bandId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Application application = new Application();
                application.setApplicationCode(resultSet.getInt("code"));

                Integer[] temp = (Integer[]) resultSet.getArray("solos_id").getArray();
                List<Integer> solosID = Arrays.asList(temp);
                application.setAppliedSoloList(solosID);

                application.setMessage(resultSet.getString("message"));

                String[] instruments = (String[]) resultSet.getArray("instruments").getArray();
                List<String> instrumentsList = Arrays.asList(instruments);
                application.setInstrumentsList(instrumentsList);

                applicationsList.add(application);
            }
            return applicationsList;

        } catch (SQLException ex) {
            throw new RepositoryException("Error fetching applications. The error was: \n" + ex.getMessage(), ex);
        }
    }

        //when a solo applies, the entries (array) of solos_id is increased. This is the method:
        public boolean addSoloToApplication(int soloId, int applicationCode){
            String sql = "UPDATE applications SET solos_id = array_append(solos_id, ?::int) WHERE  code = ?";

            try(Connection conn = connector.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)){

                preparedStatement.setInt(1, soloId);
                preparedStatement.setInt(2, applicationCode);

                return preparedStatement.executeUpdate() == 1;


            } catch (SQLException ex) {
                throw new RepositoryException(ERROR + ex.getMessage(), ex);
            }
    }

    //the band wants to add an instrument requested in the application. This is the method:
    public boolean updateApplicationInstrument(String instrument, int applicationCode){
        String sql = "update applications set instruments = array_append(instruments, ?::text) where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setString(1, instrument);
            preparedStatement.setInt(2, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    //the band wants to change the message attached to the application. This is the method:
    public boolean updateApplicationMessage(String message, int applicationCode){
        String sql = "update applications set message = ? where code = ?";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setString(1, message);
            preparedStatement.setInt(2, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex){
            throw new RepositoryException(ERROR + ex.getMessage(), ex);
        }
    }

    //the band wants to delete the application.
    public boolean deleteApplicationByCode(int applicationCode) {
        String sql = "delete from applications where code = ?";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, applicationCode);

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException ex) {
            throw new RepositoryException("Error deleting entry. The error was: \n" + ex.getMessage(), ex);
        }
    }



    //testing purpose
    public void deleteApplications() {
        String sql = "DELETE FROM applications";
        int delRecs;

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            delRecs = stmt.executeUpdate();
            if (delRecs >= 1) log.info("\t ***** Application Entries Successfully Cleaned! *****");
            resetCode();

        } catch (SQLException ex) {
            throw new RepositoryException("Error Deleting Applications", ex);
        }
    }

    private void resetCode() {
        String sql = "ALTER SEQUENCE applications_code_seq RESTART WITH 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            log.info("\t ***** Code Values reset successfully! *****");
        } catch (SQLException ex) {
            throw new RepositoryException("Error ResetCode", ex);
        }
    }






}
