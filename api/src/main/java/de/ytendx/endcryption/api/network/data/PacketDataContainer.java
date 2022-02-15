package de.ytendx.endcryption.api.network.data;

import de.ytendx.endcryption.api.EndCryption;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

public class PacketDataContainer {

    private List<Object> packetData;
    private int packetID;
    private Socket socket;

    public PacketDataContainer(int packetID, Socket socket, List<Object> packetData) {
        this.packetData = packetData;
        this.packetID = packetID;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public List<Object> getPacketData() {
        return packetData;
    }

    public int getPacketID() {
        return packetID;
    }

    public void setPacketData(List<Object> packetData) {
        this.packetData = packetData;
    }
}
