package de.ytendx.endcryption.api.network.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PacketIDDogma {

    HANDSHAKE(0),
    HANDSHAKE_ACCEPT(1),
    PROGRAMM_ABORT(2),
    MESSAGE(3),
    PUBLIC_KEY_REQUEST(4),
    PUBLIC_KEY_RESPONSE(5),
    MESSAGE_ABORT(6);

    private int id;

}
