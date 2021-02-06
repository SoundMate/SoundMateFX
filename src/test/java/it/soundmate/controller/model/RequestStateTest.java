package it.soundmate.controller.model;
import it.soundmate.model.RequestState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class RequestStateTest {

    @Test
    void requestStateTest() {
        assertSame(RequestState.REJECTED, RequestState.returnRequestState("REJECTED"));

    }

    @Test
    void requestStateTest2() {
        assertSame(RequestState.ACCEPTED, RequestState.returnRequestState("ACCEPTED"));

    }

    @Test
    void requestStateTest3() {
        assertSame(RequestState.CREATED, RequestState.returnRequestState("CREATED"));

    }

}
