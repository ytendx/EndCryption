package de.ytendx.endcryption.client;

import de.ytendx.endcryption.api.encryption.CryptionHandler;
import de.ytendx.endcryption.api.events.EventHandlerPipe;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.PacketIDDogma;
import de.ytendx.endcryption.api.network.impl.def.PacketC2CProgrammAbort;
import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.ConnectionHandler;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import de.ytendx.endcryption.client.client.ClientRegister;
import de.ytendx.endcryption.client.io.ByteHandler;
import de.ytendx.endcryption.client.io.PacketHandler;
import de.ytendx.endcryption.client.packets.PacketC2CMessage;
import de.ytendx.endcryption.client.packets.PacketC2SPublicKeyRequest;
import de.ytendx.endcryption.client.packets.PacketS2CMessageAbort;
import de.ytendx.endcryption.client.packets.PacketS2CPublicKeyResponse;
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
    private ClientRegister clientRegister;

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
                PublicKeySerialization.toString(this.connectionHandler.getCryptionHandler().getKeyPair().getPublic()), "", 4000, instanceName));

        System.out.println(CMD_PREFIX + "Sent handshake to server!");

    }

    private void initPacketRegister(PacketRegistry registry){
        registry.register(new PacketC2SOutHandshake(PacketIDDogma.HANDSHAKE.getId()));
        registry.register(new PacketS2COutHandshakeAccept(PacketIDDogma.HANDSHAKE_ACCEPT.getId()));
        registry.register(new PacketC2CMessage(PacketIDDogma.MESSAGE.getId()));
        registry.register(new PacketC2CProgrammAbort(PacketIDDogma.PROGRAMM_ABORT.getId()));
        registry.register(new PacketS2CPublicKeyResponse(PacketIDDogma.PUBLIC_KEY_RESPONSE.getId()));
        registry.register(new PacketC2SPublicKeyRequest(PacketIDDogma.PUBLIC_KEY_REQUEST.getId()));
        registry.register(new PacketS2CMessageAbort(PacketIDDogma.MESSAGE_ABORT.getId()));
    }
}
