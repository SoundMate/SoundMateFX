package it.soundmate.database.dao;

import it.soundmate.database.Connector;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.Message;
import it.soundmate.model.User;
import it.soundmate.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private final Connector connector = Connector.getInstance();
    private static final Logger log = LoggerFactory.getLogger(MessageDao.class);
    //insert
    //delete
    //get

    //sending...
    public Message insertMessage(Message message) {
        String sql = "INSERT INTO messages (id_receiver, id_sender, subject, body, sender_user_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, message.getIdReceiver());
            preparedStatement.setInt(2, message.getIdSender());
            preparedStatement.setString(3, message.getSubject());
            preparedStatement.setString(4, message.getBody());
            preparedStatement.setString(5, message.getSenderUserType().toString());

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return message.withCode(resultSet.getInt("code"));
                }
            }
        } catch (SQLException ex) {
            throw new RepositoryException("Error inserting entry: the error was: \n" + ex.getMessage(), ex);
        }
        return message.withCode(-1); //invalid code, abort (must be > 0)
    }


    public List<Message> getMessagesByUserId(User user) {
        String sql = "SELECT * FROM messages WHERE id_receiver = ?";
        ArrayList<Message> messages = new ArrayList<>();

        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message();
                message.setIdReceiver(resultSet.getInt("id_receiver"));
                message.setIdSender(resultSet.getInt("id_sender"));
                message.setMessageCode(resultSet.getInt("code"));
                message.setSubject(resultSet.getString("subject"));
                message.setBody(resultSet.getString("body"));
                message.setSenderUserType(UserType.returnUserType(resultSet.getString("sender_user_type")));
                message.setUserMessageBean(new UserDao().getSenderInfo(resultSet.getInt("id_sender")));
                log.info("Message Result: {}", message.getBody());
                messages.add(message);
            }
            return messages;
        } catch (SQLException ex) {
            throw new RepositoryException("Error fetching messages. \n" + ex.getMessage(), ex);
        }
    }


    public boolean markAsRead(Message message) {
        String sql = "update messages set is_read = ? where code = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, message.getMessageCode());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new RepositoryException("Operation failed. The error was: \n" + ex.getMessage(), ex);
        }
    }

    public boolean deleteMessageByCode(Message message) {
        String sql = "delete from messages where code = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, message.getMessageCode());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new RepositoryException("Operation failed. The error was: \n" + ex.getMessage(), ex);
        }
    }

    //testing purpose
    public void deleteAllMessages() {
        PowerUserDao powerUserDao = new PowerUserDao();
        powerUserDao.delete("DELETE FROM messages", "ALTER SEQUENCE messages_code_seq RESTART WITH 1");
    }

}
