package de.ytendx.endcryption.api;

import de.ytendx.endcryption.api.network.io.SocketAdapter;
import lombok.Getter;

import java.security.NoSuchAlgorithmException;

@Getter
public abstract class EndCryption {

    public static final String PACKET_DATA_SPLITTER = "//-packetsplit_endcryption-//";

    private String instanceName;
    private SocketAdapter adapter;

    public EndCryption(String instanceName, SocketAdapter adapter) throws NoSuchAlgorithmException {
        this.instanceName = instanceName;
        this.adapter = adapter;
    }
}
