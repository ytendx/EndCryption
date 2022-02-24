package de.ytendx.endcryption.client.chat;

public class Message {

    private final String message, senderDestinationID;
    private long receivedAt;

    public Message(String message, String senderDestinationID, long receivedAt) {
        this.message = message;
        this.senderDestinationID = senderDestinationID;
        this.receivedAt = receivedAt;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderDestinationID() {
        return senderDestinationID;
    }

    public long getReceivedAt() {
        return receivedAt;
    }
}
