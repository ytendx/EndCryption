package de.ytendx.endcryption.server.client;

import de.ytendx.endcryption.api.EndCryption;

import java.security.PublicKey;

public class Client {

    private PublicKey publicKey;
    private EndCryption clientEC;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public EndCryption getClientEC() {
        return clientEC;
    }

    public Client(PublicKey publicKey, EndCryption clientEC) {
        this.publicKey = publicKey;
        this.clientEC = clientEC;
    }

}
