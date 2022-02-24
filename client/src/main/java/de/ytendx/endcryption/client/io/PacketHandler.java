package de.ytendx.endcryption.client.io;

import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.impl.def.PacketC2CProgrammAbort;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.SocketAdapter;
import de.ytendx.endcryption.api.network.io.handler.IPacketHandler;
import de.ytendx.endcryption.client.EndCryptionClient;
import de.ytendx.endcryption.client.packets.PacketC2CMessage;
import de.ytendx.endcryption.client.packets.PacketS2CPublicKeyResponse;
import lombok.AllArgsConstructor;

import javax.swing.*;

@AllArgsConstructor
public class PacketHandler implements IPacketHandler {

    private EndCryptionClient client;

    @Override
    public boolean handle(SocketAdapter adapter, IPacket packet) {

        if(!client.getPacketRegistry().isValidPacket(packet)){
            System.out.println(EndCryptionClient.CMD_PREFIX + "The client " + adapter.getIp() + " tryed to sent an packet wich isn´t in the register.");
            return false;
        }

        if(packet instanceof PacketC2CProgrammAbort){
            System.out.println(EndCryptionClient.CMD_PREFIX + "The Server has sent a shutdown packet. Client is also shutting down...");
            System.exit(0);
            return true;
        }

        if(packet instanceof PacketS2COutHandshakeAccept){

            PacketS2COutHandshakeAccept packetS2COutHandshakeAccept = (PacketS2COutHandshakeAccept) packet;

            if(packetS2COutHandshakeAccept.getResult().equals(PacketS2COutHandshakeAccept.HandshakeResult.SUCCESFULL)){
                System.out.println(EndCryptionClient.CMD_PREFIX + "The Client is now ready to send packets cause the server sent a valid HandshakeAccept packet.");
                client.setReadyToSendMessagePackets(true);
                return true;
            }else{
                System.out.println(EndCryptionClient.CMD_PREFIX + "The Client received a HandshakePacket with a bad result. (Server did not accept request)");
                return true;
            }
        }

        if(packet instanceof PacketC2CMessage){

            PacketC2CMessage packetC2CMessage = (PacketC2CMessage) packet;

            if(!packetC2CMessage.getDestination().equals(client.getInstanceName())){
                System.out.println(EndCryptionClient.CMD_PREFIX + "The server sent a Message Packet with another destination then sent");
                return false;
            }

            System.out.println("Received Message: " + packetC2CMessage.getMessage());

        }

        if(packet instanceof PacketS2CPublicKeyResponse){

            PacketS2CPublicKeyResponse response = (PacketS2CPublicKeyResponse) packet;

            if(response.getResponseType().equals(PacketS2CPublicKeyResponse.ResponseType.SUCCESS)){



            }else{
                JOptionPane.showMessageDialog(null, "The client is not registered!", "Server", JOptionPane.ERROR_MESSAGE);
            }

        }

        return true;
    }
}
