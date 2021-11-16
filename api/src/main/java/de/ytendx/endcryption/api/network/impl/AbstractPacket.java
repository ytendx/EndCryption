package de.ytendx.endcryption.api.network.impl;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

public abstract class AbstractPacket implements IPacket {

    private final int packetID;
    private IPacketDataContainer dataContainer;

    public AbstractPacket(final int packetID, IPacketDataContainer dataContainer){
        this.packetID = packetID;
        this.dataContainer = dataContainer;
    }

    @Override
    public int getPacketID() {
        return packetID;
    }

    @Override
    public IPacketDataContainer encodeUnserializedData(){
        return this.dataContainer;
    }

    @Override
    public abstract IPacket decodeUnserialzedData(IPacketDataContainer container);
}
