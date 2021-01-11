package it.soundmate.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {

    private static final String HOST = "jdbc:postgresql://localhost:5432/Soundmate";
    private static final String USER = "postgres";
    private static final String PASSWORD = "soundmate";
    private static final Logger log = LoggerFactory.getLogger( Connector.class );
    private static Connector instance = null;
    private Connection connection;

    private Connector(){}


    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            log.info("Connected to the Database! {}",connection.getClientInfo());
        }else if (connection.isClosed()){
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        }
        return connection;
    }

    public static Connector getInstance(){
        if(instance == null){
            instance = new Connector();
        }
        return instance;
    }

}












