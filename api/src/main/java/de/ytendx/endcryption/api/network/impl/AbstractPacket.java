package de.ytendx.endcryption.api.network.impl;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
    public abstract AbstractPacket read(DataInputStream stream) throws IOException;
    @Override
    public abstract PacketDataContainer write(DataOutputStream stream) throws IOException;
}
