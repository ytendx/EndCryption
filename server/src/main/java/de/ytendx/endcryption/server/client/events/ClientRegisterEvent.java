package de.ytendx.endcryption.server.client.events;

import de.ytendx.endcryption.api.events.impl.CancelableEvent;
import de.ytendx.endcryption.server.client.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ClientRegisterEvent extends CancelableEvent {
    private Client client;
}
