package de.ytendx.endcryption.server.io;

import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IConnectionHandler;

public class ConnectionHandler implements IConnectionHandler {
    @Override
    public boolean handle(SocketAdapter adapter, String content) {
        if(content.length() > 20000){
            return false;
        }
        return true;
    }
}
