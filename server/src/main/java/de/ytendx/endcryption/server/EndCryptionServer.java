package de.ytendx.endcryption.server;

import de.ytendx.endcryption.api.encryption.CryptionHandler;
import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.impl.def.PacketC2CProgrammAbort;
import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.ConnectionHandler;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;
import de.ytendx.endcryption.server.client.ClientRegister;
import de.ytendx.endcryption.server.io.PacketHandler;
import de.ytendx.endcryption.server.io.SerializeHandler;
import de.ytendx.endcryption.server.packet.PacketC2CMessage;
import de.ytendx.endcryption.server.packet.PacketS2CMessageAbort;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class EndCryptionServer {

    private PacketRegistry packetRegistry;
    private ConnectionHandler connectionHandler;
    private ClientRegister clientRegister;
    private EventHandlerPipe eventHandlerPipe;
    public static final String CMD_PREFIX = "[EndCryption] ";

    @SneakyThrows
    public EndCryptionServer(PacketRegistry packetRegistry, SocketAdapter localData, int encryptionKeysize) {

        System.out.println(CMD_PREFIX + "Initializing EndCryptionServer...");

        System.out.println(CMD_PREFIX + "Initializing ClientRegister and the EventHandlerPipe...");

        this.eventHandlerPipe = new EventHandlerPipe();
        this.clientRegister = new ClientRegister(this.eventHandlerPipe);

        System.out.println(CMD_PREFIX + "Initialized ClientRegister and EventHandlerPipe successfully!");

        this.packetRegistry = packetRegistry;
        this.connectionHandler = new ConnectionHandler(localData, new SerializeHandler(this), new CryptionHandler(encryptionKeysize, duration ->
                System.out.println(CMD_PREFIX + "Took " + duration + "ms too initialize CryptionHandler.")));

        System.out.println(CMD_PREFIX + "Succesfully loaded ConnectionHandler. Applying Handlers...");

        connectionHandler.applyPacketHandler(new PacketHandler(this));
        connectionHandler.applyConnectionHandler(new de.ytendx.endcryption.server.io.ConnectionHandler());

        connectionHandler.applyInvalidPacketNotifyConsumer(adapter ->
                System.out.println(CMD_PREFIX + adapter.getIp() + ":" + adapter.getPort() + " -> was detected for sending an invalid packet (Not Encryption Packet)!"));

        System.out.println(CMD_PREFIX + "The handlers were applied.");

        System.out.println(CMD_PREFIX + "Adding packets to packet registry...");

        initPacketRegister(packetRegistry);

        System.out.println(CMD_PREFIX + "All packets were added to the registry.");

        System.out.println(CMD_PREFIX + "Start listening for connections...");

        connectionHandler.startDownstreamThreads(1);

        System.out.println(CMD_PREFIX + "The Threads have started. EndCryption Server is succesfully activated!");

    }

    private void initPacketRegister(PacketRegistry registry){
        registry.register(new PacketC2SOutHandshake(1));
        registry.register(new PacketS2COutHandshakeAccept(2));
        registry.register(new PacketC2CMessage(3));
        registry.register(new PacketS2CMessageAbort(4));
        registry.register(new PacketC2CProgrammAbort(5));
    }

    @SneakyThrows
    public void sendPacket(SocketAdapter socketAdapter, IPacket packet){
        connectionHandler.sendPacketData(socketAdapter, packet);
    }

}
