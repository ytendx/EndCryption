package de.ytendx.endcryption.api.network.data;

import java.util.List;

public interface IPacketDataContainer {

    int getPacketDataSize();

    List<byte[]> getPacketData();

    void setPacketData(List<byte[]> packetData);

    String serialize();

    int getPacketID();

}
