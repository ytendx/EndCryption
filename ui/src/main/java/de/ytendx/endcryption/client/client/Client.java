package de.ytendx.endcryption.client.client;

import de.ytendx.endcryption.api.EndCryption;

import java.security.PublicKey;

public class Client {

    private PublicKey publicKey;
    private String destinationID;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getDestinationID() {
        return destinationID;
    }

    public Client(PublicKey publicKey, String destinationID) {
        this.publicKey = publicKey;
        this.destinationID = destinationID;
    }

}
