package de.ytendx.endcryption.api.network;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

public interface IPacket {

    IPacketDataContainer encodeUnserializedData();
    void decodeUnserialzedData(IPacketDataContainer container);

}
