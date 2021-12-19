package de.ytendx.endcryption.server.packet;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

@Getter
public class PacketC2CMessage extends AbstractPacket {

    private String message;
    private String destination;

    public PacketC2CMessage(int packetID) {
        super(packetID);
    }

    public PacketC2CMessage(int packetID, String message, String destination) {
        super(packetID);
        this.message = message;
        this.destination = destination;
    }

    @Override
    public IPacketDataContainer encodeUnserializedData() {
        IPacketDataContainer dataContainer = new EmptyDataContainer(getPacketID());
        dataContainer.getPacketData().add(message.getBytes());
        dataContainer.getPacketData().add(destination.getBytes());
        return dataContainer;
    }

    @Override
    public IPacket decodeUnserialzedData(IPacketDataContainer container) {
        return new PacketC2CMessage(container.getPacketID(), new String(container.getPacketData().get(0)), new String(container.getPacketData().get(1)));
    }
}
