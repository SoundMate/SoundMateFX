package it.soundmate.database.dao;

import it.soundmate.bean.AddRoomBean;
import it.soundmate.bean.registerbeans.RegisterRenterBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.DBException;
import it.soundmate.database.dbexceptions.DuplicatedEmailException;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.exceptions.InputException;
import it.soundmate.exceptions.UpdateException;
import it.soundmate.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RoomRenterDao extends UserDao {

    private static final String ACC_BANNED_ERR = "\t ***** THIS ACCOUNT HAS BEEN BANNED *****";
    private static final String EMAIL_EXISTS_ERR = "\t ***** THIS EMAIL ALREADY EXISTS *****";
    private static final String ERR_INSERT = "Error inserting user";
    public static final String ROOM_CODE = "room_code";
    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(RoomRenterDao.class);

    public int registerRoomRenter(RegisterRenterBean rentBean){
        ResultSet resultSet;
        if (this.checkIfBanned(rentBean.getEmail())){
            log.error(ACC_BANNED_ERR);
            throw new DBException("Account banned");
        }else if (this.checkEmailBoolean(rentBean.getEmail())){
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

    public RoomRenterResultBean getName(int id){
        String sql = "SELECT name FROM room_renter WHERE id = ?";
        RoomRenterResultBean roomRenterResultBean = new RoomRenterResultBean();

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                roomRenterResultBean.setName(resultSet.getString("name"));
            } return roomRenterResultBean;
        }
        catch (SQLException ex) {
            throw new RepositoryException("Error fetching data. The error was: \n" + ex.getMessage(), ex);
        }
    }


    public RoomRenter getRenterByID(int id) {
        RoomRenter roomRenter = new RoomRenter();
        ResultSet resultSet;
        String query = "SELECT users.id, email, password, encoded_profile_img, name, city, address\n" +
                " FROM registered_users LEFT OUTER JOIN users ON (registered_users.id = users.id)\n" +
                " INNER JOIN room_renter rr on users.id = rr.id WHERE registered_users.id = ?";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
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
                roomRenter.setRooms(this.getRenterRooms(id));
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
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
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
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, roomRenter.getId());
            if (preparedStatement.executeUpdate() == 1) {
                roomRenter.setAddress(address);
            } else throw new UpdateException("Error updating address");
        } catch (SQLException sqlException) {
            throw new UpdateException("Error updating address, SQLException: "+sqlException.getMessage());
        }
    }

    public int addRoom(AddRoomBean addRoomBean, RoomRenter roomRenter) {
        String sql = "INSERT INTO room (id, room_price, photo, description, name) VALUES (?, ?, ?, ?, ?) RETURNING room_code";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, roomRenter.getId());
            preparedStatement.setInt(2, addRoomBean.getPrice());
            preparedStatement.setString(3, addRoomBean.getEncodedImg());
            preparedStatement.setString(4, addRoomBean.getDescription());
            preparedStatement.setString(5, addRoomBean.getName());
            if (preparedStatement.executeUpdate() == 1) {
                 ResultSet resultSet = preparedStatement.getGeneratedKeys();
                 roomRenter.setRooms(this.getRenterRooms(roomRenter.getId()));
                 if (resultSet.next()) {
                     log.info("Returning generated key: {}",  resultSet.getInt(1));
                     return resultSet.getInt(1);
                 } else throw new UpdateException("Unable to get generated key");
            } else throw new UpdateException("Error adding room");
        } catch (SQLException sqlException) {
            throw new UpdateException("Error adding room, SQLException: "+sqlException.getMessage());
        }
    }

    public List<Room> getRenterRooms(int renterID) {
        String sql = "SELECT * FROM room WHERE id = ?";
        ResultSet resultSet;
        List<Room> roomList = new ArrayList<>();
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, renterID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int roomPrice = resultSet.getInt("room_price");
                String encodedImg = resultSet.getString("photo");
                int roomCode = resultSet.getInt(ROOM_CODE);
                Room room = new Room(roomCode, name, (double) roomPrice, description, encodedImg);
                roomList.add(room);
            }
            return roomList;
        } catch (SQLException sqlException) {
            throw new UpdateException("Error getting room, SQLException: "+sqlException.getMessage());
        }
    }

    public Room getRoomByID(int roomID) {
        String query = "SELECT * FROM room WHERE room_code = (?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int renterID = resultSet.getInt("id");
                int roomCode = resultSet.getInt(ROOM_CODE);
                double price = resultSet.getInt("room_price");
                String encodedImg = resultSet.getString("photo");
                String description = resultSet.getString("description");
                String name = resultSet.getString("name");
                Room room = new Room(roomCode, name, price, description, encodedImg);
                room.setRenterID(renterID);
                return room;
            } else throw new InputException("Room not found");
        } catch (SQLException e) {
            throw new UpdateException("Room not found, SQLException: "+e.getMessage());
        }
    }

    public int insertRoom(Room room, int id){
        int roomCode = 0;
        ResultSet resultSet;
        String sql = "INSERT INTO room (id, room_price, photo, description, name) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = connector.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, room.getPrice());
            preparedStatement.setString(3, room.getEncodedImg());
            preparedStatement.setString(4, room.getDescription());
            preparedStatement.setString(5, room.getName());
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next())
                    roomCode = resultSet.getInt(ROOM_CODE);
            }
        } catch (SQLException ex){
            throw new RepositoryException("Error inserting", ex);
        } return roomCode;
    }


    public void deleteAllRoom() {
        PowerUserDao powerUserDao = new PowerUserDao();
        powerUserDao.delete("DELETE FROM room", "ALTER SEQUENCE room_room_code_seq RESTART WITH 1");
    }


    public void checkRoomAvailability(LocalDate date, LocalTime start, LocalTime end, Room room) {
        String sql = "SELECT * FROM booking WHERE room_code = (?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, room.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LocalDate resultDate = resultSet.getDate("date").toLocalDate();
                LocalTime resultStartTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime resultEndTime = resultSet.getTime("end_time").toLocalTime();
                if (this.checkRoomDateAndTime(date, start, end, resultDate, resultStartTime, resultEndTime)) {
                    throw new InputException("Room is already booked for that time");
                }
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }

    private boolean checkRoomDateAndTime(LocalDate date, LocalTime start, LocalTime end, LocalDate resultDate, LocalTime resultStartTime, LocalTime resultEndTime) {
        if (date.isEqual(resultDate)) {
            return start.isBefore(resultEndTime) && resultStartTime.isBefore(end);
        } else return false;
    }

    public void cancelBooking(BookingNotification bookingMessage) {
        String sql = "INSERT INTO notifications (sender, receiver, type, seen, booking_id) VALUES (?,?,?,?,?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookingMessage.getSender());
            preparedStatement.setInt(2, bookingMessage.getReceiver());
            preparedStatement.setString(3, MessageType.BOOK_ROOM_CANCELED.name());
            preparedStatement.setBoolean(4, false);
            preparedStatement.setInt(5, bookingMessage.getBooking().getCode());
            if (preparedStatement.executeUpdate() == 1) {
                log.info("Sent messages for deleted booking");
            }
        } catch (SQLException sqlException) {
            throw new RepositoryException(sqlException.getMessage());
        }
    }
}
