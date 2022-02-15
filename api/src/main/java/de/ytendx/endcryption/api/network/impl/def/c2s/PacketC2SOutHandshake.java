package de.ytendx.endcryption.api.network.impl.def.c2s;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.impl.AbstractPacket;
import lombok.Getter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
public class PacketC2SOutHandshake extends AbstractPacket {

    private String publicKey;
    private String name;
    private int port;
    private String ip;

    public PacketC2SOutHandshake(int packetID, String ownPublicKey, String ip, int localPort, String name) {
        super(packetID);
        this.ip = ip;
        this.port = localPort;
        this.name = name;
        this.publicKey = ownPublicKey;
    }

    public PacketC2SOutHandshake(int packetID) {
        super(packetID);
    }

    @Override
    public PacketC2SOutHandshake read(DataInputStream stream) throws IOException {
        publicKey = stream.readUTF();
        name = stream.readUTF();
        port = stream.readInt();
        name = stream.readUTF();
        ip = stream.readUTF();
        return this;
    }

    @Override
    public PacketDataContainer write(DataOutputStream stream) throws IOException {
        stream.writeUTF(publicKey);
        stream.writeUTF(name);
        stream.writeInt(port);
        stream.writeUTF(name);
        stream.writeUTF(ip);
        return null;
    }

}
