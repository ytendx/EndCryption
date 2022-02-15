package de.ytendx.endcryption.api.network.impl.def;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PacketC2CProgrammAbort extends AbstractPacket {

    public PacketC2CProgrammAbort(int packetID) {
        super(packetID);
    }

    @Override
    public PacketC2CProgrammAbort read(DataInputStream stream) {
        return null;
    }
    @Override
    public PacketDataContainer write(DataOutputStream stream) {
        return new EmptyDataContainer(getPacketID());
    }
}
