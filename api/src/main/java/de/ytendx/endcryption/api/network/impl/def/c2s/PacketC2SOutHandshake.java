package de.ytendx.endcryption.api.network.impl.def.c2s;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

import java.security.PublicKey;
import java.util.Arrays;

@Getter
public class PacketC2SOutHandshake extends AbstractPacket {

    private String publicKey;
    private String name;
    private int port;
    private String ip;

    public PacketC2SOutHandshake(int packetID, String ownPublicKey, String ip, int localPort, String name) {
        super(packetID);
        this.ip = ip;
        this.port = localPort;
        this.name = name;
    }

    public PacketC2SOutHandshake(int packetID) {
        super(packetID);
    }

    @Override
    public IPacketDataContainer encodeUnserializedData() {
        IPacketDataContainer iPacketDataContainer = new EmptyDataContainer(this.getPacketID());
        iPacketDataContainer.setPacketData(Arrays.asList(publicKey.getBytes(), ip.getBytes(), String.valueOf(port).getBytes(), name.getBytes()));
        return iPacketDataContainer;
    }

    @Override
    public PacketC2SOutHandshake decodeUnserialzedData(IPacketDataContainer container) {
        PacketC2SOutHandshake handshake = new PacketC2SOutHandshake(getPacketID(),
                new String(container.getPacketData().get(3)), new String(container.getPacketData().get(1)), Integer.valueOf(new String(container.getPacketData().get(2))), new String(container.getPacketData().get(0)));
        return handshake;
    }
}
