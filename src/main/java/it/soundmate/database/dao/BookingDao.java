package it.soundmate.database.dao;

import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class BookingDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(BookingDao.class);


    public Booking sendBookingRequest(Booking booking){
        String sql = "INSERT INTO booking (room_code, date, start_time, end_time, booker_id, is_accepted) VALUES (?, ?, ?, ?, ?, ?)";
        Date date = Date.valueOf(booking.getDate());
        Time startTime = Time.valueOf(booking.getStartTime());
        Time endTime = Time.valueOf(booking.getEndTime());

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, booking.getRoom().getCode());
            preparedStatement.setDate(2, date);
            preparedStatement.setTime(3, startTime);
            preparedStatement.setTime(4, endTime);
            preparedStatement.setInt(5, booking.getBookerUserId());
            preparedStatement.setBoolean(6, false);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return booking.withCode(resultSet.getInt("code"));
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error inserting entry: the error was: \n" + ex.getMessage(), ex);
        }
        return booking.withCode(-1); //invalid code, abort (must be > 0)
    }






}


