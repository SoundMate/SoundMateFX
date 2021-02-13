/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 21:34
 * Last edited: 29/01/21, 21:34
 */

package it.soundmate.model;

import java.util.Arrays;

public enum MessageType {
    JOIN_BAND_CONFIRMATION("Join Band Confirmation", "Joining request"),
    JOIN_BAND_CANCELED("Join Band Canceled", "Request has been canceled by the user"),
    BOOK_ROOM_CONFIRMATION("Booking Confirmation", "Booking confirmation"),
    BOOK_ROOM_CANCELED("Booking Canceled", "Booking has been canceled by the user");

    private final String name;
    private final String messageDesc;

    MessageType(String name, String messageDesc) {
        this.name = name;
        this.messageDesc = messageDesc;
    }

    public String getName() {
        return name;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public static MessageType returnMessageType(String type){
        MessageType[] messageTypes = MessageType.values();
        return Arrays.stream(messageTypes)
                .filter(currentUser -> currentUser.toString().equals(type))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Type not found: " + type));


    }
}
