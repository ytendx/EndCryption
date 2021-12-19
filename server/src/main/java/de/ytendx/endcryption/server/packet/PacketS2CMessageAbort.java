package de.ytendx.endcryption.server.packet;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

public class PacketS2CMessageAbort extends AbstractPacket {

    public PacketS2CMessageAbort(int packetID) {
        super(packetID);
    }

    @Override
    public IPacketDataContainer encodeUnserializedData() {
        return new EmptyDataContainer(getPacketID());
    }

    @Override
    public IPacket decodeUnserialzedData(IPacketDataContainer container) {
        return new PacketS2CMessageAbort(container.getPacketID());
    }
}
