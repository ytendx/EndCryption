package de.ytendx.endcryption.client.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.client.EndCryptionClient;
import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.io.IOException;

@AllArgsConstructor
public class ByteHandler implements IByteHandler {

    private EndCryptionClient client;

    @Override
    public IPacket handle(SocketAdapter adapter, PacketDataContainer dataContainer) throws IOException {
        DataInputStream inputStream = new DataInputStream(dataContainer.getSocket().getInputStream());
        IPacket packet = client.getPacketRegistry().getPacketByID(dataContainer.getPacketID());
        if(packet == null) return null;
        return packet.read(inputStream);
    }
}
