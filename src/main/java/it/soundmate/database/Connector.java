package it.soundmate.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {

    private static final Logger log = LoggerFactory.getLogger( Connector.class );
    private static final String HOST = "jdbc:postgresql://localhost:5432/SoundmateDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "soundmate";
    private static Connector instance = null;
    private Connection connection;

    private Connector() {}

    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            log.info("Connected to the Database! {}",connection.getClientInfo());
        }else if (connection.isClosed()){
            this.connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        }
        return this.connection;
    }

    public static Connector getInstance(){
        if(instance == null){
            instance = new Connector();
        }
        return instance;
    }

}







