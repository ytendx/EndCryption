package de.ytendx.endcryption.api.network.impl;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

public abstract class AbstractPacket implements IPacket {

    private final int packetID;

    public AbstractPacket(final int packetID) {
        this.packetID = packetID;
    }

    @Override
    public int getPacketID() {
        return packetID;
    }

    @Override
    public abstract IPacketDataContainer encodeUnserializedData();

    @Override
    public abstract IPacket decodeUnserialzedData(IPacketDataContainer container);
}
