package de.ytendx.endcryption.api.network.impl.def.c2s;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

import java.util.Arrays;

public class PacketC2SOutHandshake extends AbstractPacket {

    private byte[] publicKey;
    private String name;
    private int port;
    private String ip;

    public PacketC2SOutHandshake(int packetID, byte[] ownPublicKey, String ip, int localPort, String name) {
        super(packetID);
        this.publicKey = ownPublicKey;
        this.ip = ip;
        this.port = localPort;
        this.name = name;
    }

    public PacketC2SOutHandshake(int packetID) {
        super(packetID);
    }

    @Override
    public IPacketDataContainer encodeUnserializedData() {
        IPacketDataContainer iPacketDataContainer = new EmptyDataContainer();
        iPacketDataContainer.setPacketData(Arrays.asList(name.getBytes(), ip.getBytes(), String.valueOf(port).getBytes(), publicKey));
        return iPacketDataContainer;
    }

    @Override
    public PacketC2SOutHandshake decodeUnserialzedData(IPacketDataContainer container) {
        PacketC2SOutHandshake handshake = new PacketC2SOutHandshake(getPacketID(),
                container.getPacketData().get(3), new String(container.getPacketData().get(1)), Integer.valueOf(new String(container.getPacketData().get(2))), new String(container.getPacketData().get(0)));
        return handshake;
    }
}
