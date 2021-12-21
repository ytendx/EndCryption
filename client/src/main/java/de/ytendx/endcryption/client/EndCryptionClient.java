package de.ytendx.endcryption.client;

import de.ytendx.endcryption.api.encryption.CryptionHandler;
import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.def.PacketC2CProgrammAbort;
import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.ConnectionHandler;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import de.ytendx.endcryption.client.io.ByteHandler;
import de.ytendx.endcryption.client.io.PacketHandler;
import de.ytendx.endcryption.client.packets.PacketC2CMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter @Setter
public class EndCryptionClient {

    public static final String CMD_PREFIX = "[EndCryption] ";
    private ConnectionHandler connectionHandler;
    private PacketRegistry packetRegistry;
    private EventHandlerPipe eventHandlerPipe;
    private boolean isReadyToSendMessagePackets = false;
    private String instanceName;

    @SneakyThrows
    public EndCryptionClient(String name, SocketAdapter adapter) {

        System.out.println(CMD_PREFIX + "Initializing EndCryptionClient...");

        this.eventHandlerPipe = new EventHandlerPipe();
        this.instanceName = name;

        System.out.println(CMD_PREFIX + "EventHandlerPipe was initialized!");

        this.packetRegistry = new PacketRegistry();

        System.out.println(CMD_PREFIX + "PacketRegistry was initialized! Adding packets...");

        initPacketRegister(this.packetRegistry);

        System.out.println(CMD_PREFIX + this.packetRegistry.getPackets().size() + " packets were succesfully registered. Initializing ConnectionHandler...");

        this.connectionHandler = new ConnectionHandler(new SocketAdapter("localhost", 4000), new ByteHandler(this), new CryptionHandler(-1, aLong -> {
            System.out.println(CMD_PREFIX + "It took " + aLong + "ms to create the keypair.");
        }));

        this.connectionHandler.applyPacketHandler(new PacketHandler(this));
        this.connectionHandler.startDownstreamThreads(1);

        System.out.println(CMD_PREFIX + "Succesfully initialized the ConnectionHandler. Listening to connections...");

        this.connectionHandler.sendPacketData(adapter, new PacketC2SOutHandshake(1,
                PublicKeySerialization.toString(this.connectionHandler.getCryptionHandler().getKeyPair().getPublic()), "", 4000, instanceName).encodeUnserializedData());

        System.out.println(CMD_PREFIX + "Sent handshake to server!");

    }

    private void initPacketRegister(PacketRegistry registry){
        registry.register(new PacketC2SOutHandshake(1));
        registry.register(new PacketS2COutHandshakeAccept(2));
        registry.register(new PacketC2CMessage(3));
        registry.register(new PacketC2CProgrammAbort(5));
    }
}
