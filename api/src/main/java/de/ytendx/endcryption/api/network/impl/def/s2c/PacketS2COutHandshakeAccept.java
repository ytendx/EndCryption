package de.ytendx.endcryption.api.network.impl.def.s2c;

import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

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
    public IPacketDataContainer encodeUnserializedData() {
        IPacketDataContainer dataContainer = new EmptyDataContainer(this.getPacketID());
        dataContainer.getPacketData().add(result.toString().getBytes());
        return dataContainer;
    }

    @Override
    public PacketS2COutHandshakeAccept decodeUnserialzedData(IPacketDataContainer container) {
        PacketS2COutHandshakeAccept handshakeAccept = new PacketS2COutHandshakeAccept(getPacketID(), HandshakeResult.valueOf(new String(container.getPacketData().get(0))));
        return handshakeAccept;
    }

    public static enum HandshakeResult {
        SUCCESFULL, INVALID_NAME, INVALID_DATA, OTHER_CANCELLING;
    }
}
