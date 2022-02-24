package de.ytendx.endcryption.api.network.impl.def.s2c;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Getter
public class PacketS2COutHandshakeAccept extends AbstractPacket {

    private HandshakeResult result;

    public PacketS2COutHandshakeAccept(int packetID, HandshakeResult result) {
        super(packetID);
        this.result = result;
    }

    public PacketS2COutHandshakeAccept(int packetID) {
        super(packetID);
    }

    @Override
    public PacketS2COutHandshakeAccept read(DataInputStream stream) throws IOException {
        result = HandshakeResult.valueOf(stream.readUTF());
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(result.toString());
        return new PacketDataContainer(this.getPacketID(), null, Arrays.asList(result));
    }

    public static enum HandshakeResult {
        SUCCESFULL, INVALID_NAME, INVALID_DATA, OTHER_CANCELLING;
    }
}
