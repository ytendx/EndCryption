package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.io.SocketAdapter;

public interface IPacketHandler {

    boolean handle(SocketAdapter adapter, IPacket packet);

}
