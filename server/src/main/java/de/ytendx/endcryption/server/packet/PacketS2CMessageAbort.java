package de.ytendx.endcryption.server.packet;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketS2CMessageAbort extends AbstractPacket {

    public PacketS2CMessageAbort(int packetID) {
        super(packetID);
    }

    @Override
    public PacketS2CMessageAbort read(DataInputStream stream) throws IOException {
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        return null;
    }

}
