package de.ytendx.endcryption.server;

import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;

public class Launcher {

    public static void main(String[] args) {
        System.out.println("Starting EndCryption-Server Launcher...");
        new EndCryptionServer(new PacketRegistry(), new SocketAdapter("localhost", 4000), -1);
    }

}
