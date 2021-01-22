package it.soundmate.controller.database;

import it.soundmate.database.Connector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConnectorPropertiesTest {

    static Connector connector = Connector.getInstance();


    @Test
    void hostTest(){
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/SoundmateDB", connector.getHOST());
    }

    @Test
    void userTest(){
        Assertions.assertEquals("postgres", connector.getUSER());
    }

    @Test
    void pswTest(){
        Assertions.assertEquals("soundmate", connector.getPASSWORD());
    }
}
