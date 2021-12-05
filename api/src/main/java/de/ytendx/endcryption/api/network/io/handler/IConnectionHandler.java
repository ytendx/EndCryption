package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.io.SocketAdapter;

import java.util.List;

public interface IConnectionHandler {

    boolean handle(SocketAdapter adapter, String content);

}
