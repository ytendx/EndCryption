package de.ytendx.endcryption.server.client;

import de.ytendx.endcryption.api.EndCryption;
import de.ytendx.endcryption.api.network.io.SocketAdapter;

import java.security.NoSuchAlgorithmException;

public class ServerEndCryption extends EndCryption {
    public ServerEndCryption(String instanceName, SocketAdapter adapter) throws NoSuchAlgorithmException {
        super(instanceName, adapter);
    }
}
