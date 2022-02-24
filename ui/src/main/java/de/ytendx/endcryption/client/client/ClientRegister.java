package de.ytendx.endcryption.client.client;

import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.api.network.impl.PacketIDDogma;
import de.ytendx.endcryption.api.network.io.ConnectionHandler;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.client.packets.PacketC2SPublicKeyRequest;

import java.io.IOException;
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

    public void register(String destination, ConnectionHandler connectionHandler, SocketAdapter master){
        try {
            connectionHandler.sendPacketData(master, new PacketC2SPublicKeyRequest(PacketIDDogma.PUBLIC_KEY_REQUEST.getId(), destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
