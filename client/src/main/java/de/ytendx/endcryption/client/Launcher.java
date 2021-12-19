package de.ytendx.endcryption.client;

import de.ytendx.endcryption.api.network.io.SocketAdapter;

public class Launcher {

    public static void main(String[] args) {

        System.out.println("Launcher: Launching EndCryptionClient...");

        if(args.length != 3){
            System.out.println("Launcher: False Usage! Use: java -jar client.jar <ServerIP> <ServerPort> <ClientInstance>");
            return;
        }

        new EndCryptionClient(args[2], new SocketAdapter(args[0], Integer.parseInt(args[1])));
    }

}
