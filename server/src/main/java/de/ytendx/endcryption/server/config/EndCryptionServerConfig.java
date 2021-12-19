package de.ytendx.endcryption.server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class EndCryptionServerConfig {

    private int maxMessageLength;

    public static EndCryptionServerConfig getDefaultConfig(){
        return new EndCryptionServerConfig(5000);
    }
}
