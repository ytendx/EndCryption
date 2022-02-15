package de.ytendx.endcryption.server.io;

import com.sun.imageio.plugins.common.InputStreamAdapter;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IConnectionHandler;

import java.io.*;

public class ConnectionHandler implements IConnectionHandler {
    @Override
    public boolean handle(SocketAdapter adapter, DataInputStream content) {
        return true;
    }
}
