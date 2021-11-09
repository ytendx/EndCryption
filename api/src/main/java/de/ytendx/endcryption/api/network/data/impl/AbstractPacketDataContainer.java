package de.ytendx.endcryption.api.network.data.impl;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

import java.util.List;

public class AbstractPacketDataContainer implements IPacketDataContainer {

    private List<String> packetData;

    public AbstractPacketDataContainer(List<String> packetData) {
        this.packetData = packetData;
    }

    @Override
    public int getPacketDataSize() {
        return this.packetData.size();
    }

    @Override
    public List<String> getPacketData() {
        return packetData;
    }

    @Override
    public void setPacketData(List<String> packetData) {
        this.packetData = packetData;
    }
}
