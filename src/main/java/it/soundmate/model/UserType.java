/*
 * Copyright (c) 2020.
 * This file was created by Soundmate organization Lorenzo Pantano & Matteo D'Alessandro
 * Last Modified: 12/12/20, 14:41
 */

package it.soundmate.model;

import java.util.Arrays;


public enum UserType {
    BAND, SOLO, ROOM_RENTER;

    public static UserType returnUserType(String userType){
        UserType[] userTypes = UserType.values();
        return Arrays.stream(userTypes)
                .filter(currentUser -> currentUser.toString().equals(userType))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("UserType not found: " + userType));


    }

}
