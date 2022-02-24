package de.ytendx.endcryption.client.packets;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
public class PacketC2CMessage extends AbstractPacket {

    private String message;
    private String destination;

    public PacketC2CMessage(int packetID) {
        super(packetID);
    }

    @Override
    public PacketC2CMessage read(DataInputStream stream) throws IOException {
        message = stream.readUTF();
        destination = stream.readUTF();
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(message);
        stream.writeUTF(destination);
        return new EmptyDataContainer(getPacketID());
    }

    public PacketC2CMessage(int packetID, String message, String destination) {
        super(packetID);
        this.message = message;
        this.destination = destination;
    }
}
