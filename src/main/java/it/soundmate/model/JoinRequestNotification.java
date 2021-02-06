/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 06/02/21, 15:55
 * Last edited: 06/02/21, 15:55
 */

package it.soundmate.model;

public class JoinRequestNotification extends Notification {

    private JoinRequest joinRequest;

    public JoinRequestNotification(int sender, int receiver, MessageType messageType, boolean seen) {
        super(sender, receiver, messageType, seen);
    }

    public JoinRequest getJoinRequest() {
        return joinRequest;
    }

    public void setJoinRequest(JoinRequest joinRequest) {
        this.joinRequest = joinRequest;
    }
}
