package de.ytendx.endcryption.server.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.server.EndCryptionServer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.IOException;

@AllArgsConstructor
@Getter
public class SerializeHandler implements IByteHandler {

    private EndCryptionServer server;

    @Override
    public IPacket handle(SocketAdapter adapter, PacketDataContainer dataContainer) throws IOException {
        DataInputStream inputStream = new DataInputStream(dataContainer.getSocket().getInputStream());
        IPacket packet = server.getPacketRegistry().getPacketByID(dataContainer.getPacketID());
        if(packet == null) return null;
        return packet.read(inputStream);
    }
}
