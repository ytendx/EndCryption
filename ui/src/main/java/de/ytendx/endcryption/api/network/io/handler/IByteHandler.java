package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;

import java.io.IOException;

public interface IByteHandler {

    IPacket handle(SocketAdapter adapter, PacketDataContainer dataContainer) throws IOException;

}
