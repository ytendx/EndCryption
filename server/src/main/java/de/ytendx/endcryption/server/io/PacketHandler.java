package de.ytendx.endcryption.server.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IPacketHandler;
import de.ytendx.endcryption.api.util.PublicKeySerialization;
import de.ytendx.endcryption.server.EndCryptionServer;
import de.ytendx.endcryption.server.client.Client;
import de.ytendx.endcryption.server.client.ServerEndCryption;
import de.ytendx.endcryption.server.packet.PacketC2CMessage;
import de.ytendx.endcryption.server.packet.PacketS2CMessageAbort;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class PacketHandler implements IPacketHandler {

    private EndCryptionServer server;

    @SneakyThrows
    @Override
    public boolean handle(SocketAdapter adapter, IPacket packet) {

        if(!server.getPacketRegistry().isValidPacket(packet)){
            System.out.println(EndCryptionServer.CMD_PREFIX + "The client " + adapter.getIp() + " tryed to sent an packet wich isn´t in the register.");
            return false;
        }

        if(packet instanceof PacketC2SOutHandshake){

            PacketC2SOutHandshake packetC2SOutHandshake = (PacketC2SOutHandshake) packet;

            if(server.getClientRegister().containsClient(packetC2SOutHandshake.getName())){
                server.sendPacket(adapter, new PacketS2COutHandshakeAccept(2, PacketS2COutHandshakeAccept.HandshakeResult.INVALID_NAME));
                System.out.println(EndCryptionServer.CMD_PREFIX + "The client " + adapter.getIp() + " tryed to register with an name that is already in use. (HandshakeResult sent)");
                return false;
            }

            server.getClientRegister().register(new Client(PublicKeySerialization.fromString(packetC2SOutHandshake.getPublicKey()),
                    new ServerEndCryption(packetC2SOutHandshake.getName(), new SocketAdapter(packetC2SOutHandshake.getIp(), packetC2SOutHandshake.getPort()))), cancelled -> {

                if(cancelled){
                    System.out.println(EndCryptionServer.CMD_PREFIX + "The " + packet.getClass().getSimpleName() + " got cancelled by an Event.");
                    return;
                }

                server.sendPacket(adapter, new PacketS2COutHandshakeAccept(server.getPacketRegistry().getPacketIDByClazz(PacketS2COutHandshakeAccept.class), PacketS2COutHandshakeAccept.HandshakeResult.SUCCESFULL));

            });
        }

        if(packet instanceof PacketC2CMessage){

            PacketC2CMessage packetC2CMessage = (PacketC2CMessage) packet;

            if(!server.getClientRegister().containsClient(packetC2CMessage.getDestination())){
                System.out.println(EndCryptionServer.CMD_PREFIX + "The client " + adapter.getIp() + " tryed to sent an " + packet.getClass().getSimpleName() + " with invalid destination.");
                server.sendPacket(adapter, new PacketS2CMessageAbort(server.getPacketRegistry().getPacketIDByClazz(PacketS2CMessageAbort.class)));
                return false;
            }

            if(packetC2CMessage.getMessage().length() > 5000 || packetC2CMessage.getMessage().length() < 1){
                System.out.println(EndCryptionServer.CMD_PREFIX + "The client " + adapter.getIp() + " tryed to sent an " + packet.getClass().getSimpleName() + " with invalid message.");
                server.sendPacket(adapter, new PacketS2CMessageAbort(server.getPacketRegistry().getPacketIDByClazz(PacketS2CMessageAbort.class)));
                return false;
            }

            Client client = server.getClientRegister().getByName(packetC2CMessage.getDestination());

            server.sendPacket(client.getClientEC().getAdapter(),
                    new PacketC2CMessage(server.getPacketRegistry().getPacketIDByClazz(PacketC2CMessage.class), packetC2CMessage.getMessage(), client.getClientEC().getInstanceName()));

            System.out.println(EndCryptionServer.CMD_PREFIX + "The client " + adapter.getIp() + " sent an message to " + client.getClientEC().getAdapter().getIp() + " succesfully");

        }

        return true;
    }
}
