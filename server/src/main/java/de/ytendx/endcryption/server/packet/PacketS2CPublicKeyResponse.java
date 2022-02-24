package de.ytendx.endcryption.server.packet;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Getter
public class PacketS2CPublicKeyResponse extends AbstractPacket {

    private ResponseType responseType;
    private PublicKey publicKey;
    private String destinationID;

    public PacketS2CPublicKeyResponse(int packetID) {
        super(packetID);
    }

    public PacketS2CPublicKeyResponse(int packetID, ResponseType responseType, PublicKey publicKey, String destinationID) {
        super(packetID);
        this.responseType = responseType;
        this.publicKey = publicKey;
        this.destinationID = destinationID;
    }

    @Override
    public AbstractPacket read(DataInputStream stream) throws IOException {
        responseType = ResponseType.valueOf(stream.readUTF());
        try {
            publicKey = PublicKeySerialization.fromString(stream.readUTF());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(responseType.toString());
        stream.writeUTF(PublicKeySerialization.toString(publicKey));
        return null;
    }

    public static enum ResponseType{
        SUCCESS, NOT_REGISTERED_DESTINATION;
    }
}
