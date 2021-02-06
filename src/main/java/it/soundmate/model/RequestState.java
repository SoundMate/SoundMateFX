package it.soundmate.model;

import java.util.Arrays;

public enum RequestState {

    ACCEPTED, REJECTED, CREATED;

    public static RequestState returnRequestState(String requestState){
        RequestState[] requestStates = RequestState.values();
        return Arrays.stream(requestStates)
                .filter(currentUser -> currentUser.toString().equals(requestState))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("UserType not found: " + requestState));


    }

}
