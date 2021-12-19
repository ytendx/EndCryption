package de.ytendx.endcryption.api.network.data.impl;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;

import java.util.List;

public class AbstractPacketDataContainer implements IPacketDataContainer {

    private List<byte[]> packetData;
    private int packetID;

    public AbstractPacketDataContainer(int packetID, List<byte[]> packetData) {
        this.packetData = packetData;
        this.packetID = packetID;
    }

    @Override
    public int getPacketDataSize() {
        return this.packetData.size();
    }

    @Override
    public List<byte[]> getPacketData() {
        return packetData;
    }

    @Override
    public void setPacketData(List<byte[]> packetData) {
        this.packetData = packetData;
    }

    @Override
    public String serialize() {
        StringBuilder builder = new StringBuilder();
        for (byte[] bytes : packetData) {
            builder.append(new String(bytes));
        }
        return builder.toString(); // TODO Recode to GsonBuilder
    }

    @Override
    public int getPacketID() {
        return packetID;
    }
}
