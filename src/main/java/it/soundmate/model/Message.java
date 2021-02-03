package it.soundmate.model;

public class Message {

    private int messageCode;
    private int idSender;
    private int idReceiver;
    private String subject;
    private String body;

    public Message() {
    }

    public Message(int idSender, int idReceiver, String subject, String body) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.subject = subject;
        this.body = body;
    }

    public Message withCode(int code){
        Message newMessage = new Message();
        newMessage.setIdSender(this.idSender);
        newMessage.setIdReceiver(this.idReceiver);
        newMessage.setSubject(this.subject);
        newMessage.setBody(this.body);
        newMessage.setMessageCode(code);
        return newMessage;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
