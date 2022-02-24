package de.ytendx.endcryption.client.chat;

import java.util.LinkedList;
import java.util.List;

public class Chat {

    private final List<Message> messages;
    private final String displayName;
    private String destinationID;

    public Chat(String displayName) {
        this.messages = new LinkedList<>();
        this.displayName = displayName;
        this.destinationID = "Unknown";
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public Chat(List<Message> messages, String displayName, String destinationID) {
        this.messages = messages;
        this.displayName = displayName;
        this.destinationID = destinationID;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getDisplayName() {
        return displayName;
    }
}
