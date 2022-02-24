package de.ytendx.endcryption.client.packets;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
public class PacketS2CPublicKeyResponse extends AbstractPacket {

    private ResponseType responseType;
    private byte[] publicKey;

    public PacketS2CPublicKeyResponse(int packetID) {
        super(packetID);
    }

    public PacketS2CPublicKeyResponse(int packetID, ResponseType responseType, byte[] publicKey) {
        super(packetID);
        this.responseType = responseType;
        this.publicKey = publicKey;
    }

    @Override
    public AbstractPacket read(DataInputStream stream) throws IOException {
        responseType = ResponseType.valueOf(stream.readUTF());
        publicKey = stream.readUTF().getBytes();
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(responseType.toString());
        stream.writeUTF(new String(publicKey));
        return null;
    }

    public static enum ResponseType{
        SUCCESS, NOT_REGISTERED_DESTINATION;
    }
}
