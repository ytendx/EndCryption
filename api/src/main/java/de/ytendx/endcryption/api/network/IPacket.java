package de.ytendx.endcryption.api.network;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPacket {

    int getPacketID();

    IPacket read(DataInputStream stream) throws IOException;
    PacketDataContainer write(DataOutputStream stream) throws IOException;

}
