package de.ytendx.endcryption.client;

import de.ytendx.endcryption.api.network.io.SocketAdapter;

public class Launcher {

    public static void main(String[] args) {

        System.out.println("Launcher: Launching EndCryptionClient...");

        /*if(args.length != 3){
            System.out.println("Launcher: False Usage! Use: java -jar client.jar <ServerIP> <ServerPort> <ClientInstance>");
            return;
        }*/

        new EndCryptionClient("Schwurblibub", new SocketAdapter("185.248.140.51", 4000));
        System.out.println(0x0A + " | " + 0x0B);
    }

}
