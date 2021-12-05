package de.ytendx.endcryption.server.client.events;

import de.ytendx.endcryption.api.events.Event;
import de.ytendx.endcryption.server.client.Client;

public class ClientUnregisterEvent extends Event {

    private Client client;

    public ClientUnregisterEvent(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
