package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.io.SocketAdapter;

public interface IConnectionHandler {

    boolean handle(SocketAdapter adapter, String content);

}
