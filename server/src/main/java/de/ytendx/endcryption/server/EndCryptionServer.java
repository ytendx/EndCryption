package de.ytendx.endcryption.server;

import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.api.network.io.ConnectionHandler;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;

public class EndCryptionServer {

    private PacketRegistry packetRegistry;
    private ConnectionHandler connectionHandler;
    private EventHandlerPipe eventHandlerPipe;

}
