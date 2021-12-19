package de.ytendx.endcryption.api.network.io.packet;

import de.ytendx.endcryption.api.network.IPacket;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class PacketRegistry {

    private CopyOnWriteArrayList<IPacket> packets;

    public PacketRegistry() {
        this.packets = new CopyOnWriteArrayList<>();
    }

    public void register(IPacket packet) {
        if (!packets.contains(packet))
            packets.add(packet);
    }

    public boolean isValidPacket(IPacket packet) {
        if (!packets.contains(packet)) {
            for (IPacket packets : packets) {
                if (packets.getPacketID() == packet.getPacketID())
                    return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public IPacket getPacketByID(int packetID) {
        for (IPacket packets : packets) {
            if (packets.getPacketID() == packetID)
                return packets;
        }
        return null;
    }

    public int getPacketIDByClazz(Class<? extends IPacket> packetClazz){
        for(IPacket packet : packets)
            if(packet.getClass().isInstance(packetClazz)) return packet.getPacketID();
        throw new NullPointerException("No packet found");
    }
}
