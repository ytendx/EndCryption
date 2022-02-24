package de.ytendx.endcryption.client.client;

import de.ytendx.endcryption.api.events.EventHandlerPipe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public void register(Client client){
        clients.add(client);
    }

    public void unregister(Client client){
        if(clients.contains(client)) clients.remove(client);
    }

    public boolean containsClient(String name){
        for (Client client : clients){
            if(client.getDestinationID().equals(name)) return true;
        }
        return false;
    }

    public Client getByName(String name){
        for(Client client : clients) if(client.getDestinationID().equals(name)) return client;
        return null;
    }

}
