package de.ytendx.endcryption.api.network.impl.def;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

public class PacketC2CProgrammAbort extends AbstractPacket {

    public PacketC2CProgrammAbort(int packetID) {
        super(packetID);
    }

    @Override
    public IPacketDataContainer encodeUnserializedData() {
        return null;
    }

    @Override
    public IPacket decodeUnserialzedData(IPacketDataContainer container) {
        return null;
    }
}
