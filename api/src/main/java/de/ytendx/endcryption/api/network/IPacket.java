package de.ytendx.endcryption.api.network;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

public interface IPacket {

    int getPacketID();

    IPacketDataContainer encodeUnserializedData();

    IPacket decodeUnserialzedData(IPacketDataContainer container);

}
