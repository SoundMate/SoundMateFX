/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 11/12/20, 15:42
 */

package it.soundmate.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    /* Singleton */

    private Connection connection;
    private static Connector instance = null;

    public static Connector getInstance(){
        if (instance == null){
            instance = new Connector();
        }
        return instance;
    }

    public Connector(){
        String url = "jdbc:postgresql://localhost/SoundmateDB";
        try {
            this.connection = DriverManager.getConnection(url, "postgres", "soundmate");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.connection;
    }
}
