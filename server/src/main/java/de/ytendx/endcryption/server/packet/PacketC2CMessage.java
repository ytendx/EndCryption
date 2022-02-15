package de.ytendx.endcryption.server.packet;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
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
        return null;
    }

    public PacketC2CMessage(int packetID, String message, String destination) {
        super(packetID);
        this.message = message;
        this.destination = destination;
    }

}
