package de.ytendx.endcryption.client.packets;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketC2SPublicKeyRequest extends AbstractPacket {

    private String destination;

    public PacketC2SPublicKeyRequest(int packetID) {
        super(packetID);
    }

    public PacketC2SPublicKeyRequest(int packetID, String destination) {
        super(packetID);
        this.destination = destination;
    }

    @Override
    public PacketC2SPublicKeyRequest read(DataInputStream stream) throws IOException {
        destination = stream.readUTF();
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(destination);
        return new EmptyDataContainer(getPacketID());
    }
}
