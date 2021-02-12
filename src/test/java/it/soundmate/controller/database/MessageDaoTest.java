package it.soundmate.controller.database;

import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.Connector;
import it.soundmate.database.dao.MessageDao;
import it.soundmate.database.dao.UserDao;
import it.soundmate.model.Message;
import it.soundmate.model.UserType;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageDaoTest {


    private final Connector connector = Connector.getInstance();
    private static final MessageDao sut = new MessageDao();
    private static final UserDao userDao = new UserDao();

    @BeforeAll
    static void setUp(){
        RegisterSoloBean user1 = new RegisterSoloBean("pippo@", "asd", "pippo", "pluto", "Rome");
        RegisterSoloBean user2 = new RegisterSoloBean("pluto@", "asd", "pluto", "pippo", "Florence");
        userDao.register(user1);
        userDao.register(user2);
    }


    @Test
    @Order(1)
    void insertMessageTest(){
        Message message = new Message(1, 2, "ciao", "questo è un messaggio per te", UserType.SOLO);
        Assertions.assertEquals(1, sut.insertMessage(message).getMessageCode());
    }

    @Test
    @Order(2)
    void insertAndDeleteUseCase(){
        Message message = new Message(1, 2, "ciao", "questo è un messaggio per te", UserType.BAND);
        Message toDeleteMsg = sut.insertMessage(message);
        Assertions.assertTrue(sut.deleteMessageByCode(toDeleteMsg));
    }

    @AfterAll
     static void tearDown(){
        userDao.deleteAll();
        sut.deleteAllMessages();
        userDao.deleteAll();
    }


}
