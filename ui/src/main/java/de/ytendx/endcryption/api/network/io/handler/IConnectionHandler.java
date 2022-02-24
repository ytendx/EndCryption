package de.ytendx.endcryption.api.network.io.handler;

import de.ytendx.endcryption.api.network.io.SocketAdapter;

import java.io.DataInputStream;

public interface IConnectionHandler {

    boolean handle(SocketAdapter adapter, DataInputStream content);

}
