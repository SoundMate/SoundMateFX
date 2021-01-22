package it.soundmate.controller.database;

import it.soundmate.database.Connector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ConnectorPropertiesTest {



    @Test
    void connectionTest(){
        Assertions.assertDoesNotThrow(() -> Connector.getInstance().getConnection(), "Server is Down!");
    }

    @Test
    void connectorTest(){
        Assertions.assertDoesNotThrow(Connector::getInstance, "Error, illegal properties");
    }

}
