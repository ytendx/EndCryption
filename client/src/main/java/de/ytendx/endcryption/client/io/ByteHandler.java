package de.ytendx.endcryption.client.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.client.EndCryptionClient;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ByteHandler implements IByteHandler {

    private EndCryptionClient client;

    @Override
    public IPacket handle(SocketAdapter adapter, IPacketDataContainer dataContainer) {
        if(client.getPacketRegistry().getPacketByID(dataContainer.getPacketID()) == null){
            return null;
        }

        if(dataContainer.getPacketID() < 1){
            return null;
        }

        IPacket packet = client.getPacketRegistry().getPacketByID(dataContainer.getPacketID());
        return packet;
    }
}
