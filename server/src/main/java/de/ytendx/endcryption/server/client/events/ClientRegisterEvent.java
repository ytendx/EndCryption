package de.ytendx.endcryption.server.client.events;

import de.ytendx.endcryption.api.events.impl.CancelableEvent;
import de.ytendx.endcryption.server.client.Client;

public class ClientRegisterEvent extends CancelableEvent {

    private Client client;

    public ClientRegisterEvent(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
