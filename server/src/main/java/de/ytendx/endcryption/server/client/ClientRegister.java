package de.ytendx.endcryption.server.client;

import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.server.client.events.ClientRegisterEvent;
import de.ytendx.endcryption.server.client.events.ClientUnregisterEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class ClientRegister {

    private List<Client> clients;
    private EventHandlerPipe eventHandlerPipe;

    public ClientRegister(EventHandlerPipe eventHandlerPipe) {
        this.clients = new CopyOnWriteArrayList<>();
        this.eventHandlerPipe = eventHandlerPipe;
    }

    public List<Client> getRawClientList() {
        return clients;
    }

    public void register(Client client, Consumer<Boolean> callback){
        this.eventHandlerPipe.call(new ClientRegisterEvent(client), canceled -> {
            if (!canceled) clients.add(client);
            callback.accept(canceled);
        });
    }

    public void unregister(Client client){
        if(clients.contains(client)) clients.remove(client);
        this.eventHandlerPipe.call(new ClientUnregisterEvent(client));
    }

}
