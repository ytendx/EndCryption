package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.io.SocketAdapter;

public interface IByteHandler {

    IPacket handle(SocketAdapter adapter, IPacketDataContainer dataContainer);

}
