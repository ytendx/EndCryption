package de.ytendx.endcryption.api.network.data;

import java.util.List;

public interface IPacketDataContainer {

    int getPacketDataSize();
    List<String> getPacketData();
    void setPacketData(List<String> packetData);

}
