package it.soundmate.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserType {
    BAND, SOLO, BAND_MANAGER, ROOM_RENTER;

    public static UserType returnUserType(String userType){
        UserType[] userTypes = UserType.values();
        return Arrays.stream(userTypes)
                .filter(currentUser -> currentUser.toString().equals(userType))
                .findFirst().get();

    }

}
