/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 30/01/21, 16:06
 * Last edited: 30/01/21, 16:06
 */

package it.soundmate.model;

public class JoinRequest extends Notification {

    public JoinRequest(int sender, int receiver, MessageType messageType, boolean seen) {
        super(sender, receiver, messageType, seen);
    }
}
