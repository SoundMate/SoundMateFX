package it.soundmate.controller.model;

import it.soundmate.model.UserType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    @Test
    void testEnum() {
        UserType testt = UserType.returnUserType("SOLO");
        System.out.println(testt);
        assertSame(UserType.SOLO, UserType.returnUserType("SOLO"));

    }

    @Test
    void testEnum2() {
        assertSame(UserType.BAND, UserType.returnUserType("BAND"));

    }
}