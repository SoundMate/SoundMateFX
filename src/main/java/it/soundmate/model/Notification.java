/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 29/01/21, 21:37
 * Last edited: 29/01/21, 21:37
 */

package it.soundmate.model;

public abstract class Notification {

    private int sender;
    private int receiver;
    private MessageType messageType;
    private boolean seen;
    private int messageId;

    protected Notification(int sender, int receiver, MessageType messageType, boolean seen) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageType = messageType;
        this.seen = seen;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
