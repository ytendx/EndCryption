package de.ytendx.endcryption.server.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.server.EndCryptionServer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SerializeHandler implements IByteHandler {

    private EndCryptionServer server;

    @Override
    public IPacket handle(SocketAdapter adapter, IPacketDataContainer dataContainer) {
        if(server.getPacketRegistry().getPacketByID(dataContainer.getPacketID()) == null){
            return null;
        }
        if(dataContainer.getPacketID() < 1 || dataContainer.getPacketID() > server.getPacketRegistry().getPackets().size()){
            return null;
        }
        IPacket packetClass = server.getPacketRegistry().getPacketByID(dataContainer.getPacketID());
        return packetClass.decodeUnserialzedData(dataContainer);
    }
}
