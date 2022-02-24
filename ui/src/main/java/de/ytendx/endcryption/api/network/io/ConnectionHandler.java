package de.ytendx.endcryption.api.network.io;

import de.ytendx.endcryption.api.encryption.CryptionHandler;
import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.PacketDataContainer;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.api.network.io.handler.IConnectionHandler;
import de.ytendx.endcryption.api.network.io.handler.IPacketHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ConnectionHandler {

    private IByteHandler byteHandler;
    private IPacketHandler packetHandler;
    private CryptionHandler cryptionHandler;
    private SocketAdapter localData;
    private IConnectionHandler connectionHandler;
    private Consumer<SocketAdapter> invalidPacketHandler;

    public ConnectionHandler(SocketAdapter localData, IByteHandler byteHandler, CryptionHandler cryptionHandler) {
        this.byteHandler = byteHandler;
        this.cryptionHandler = cryptionHandler;
        this.localData = localData;
    }

    public IByteHandler getByteHandler() {
        return byteHandler;
    }

    public IPacketHandler getPacketHandler() {
        return packetHandler;
    }

    public CryptionHandler getCryptionHandler() {
        return cryptionHandler;
    }

    public SocketAdapter getLocalData() {
        return localData;
    }

    public IConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public Consumer<SocketAdapter> getInvalidPacketHandler() {
        return invalidPacketHandler;
    }

    public boolean sendPacketData(SocketAdapter adapter, IPacket data) throws IOException {
        final Socket socket = new Socket(adapter.getIp(), adapter.getPort());
        socket.setSoTimeout(100);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.write(data.getPacketID());
        data.write(outputStream);
        outputStream.flush();
        outputStream.close();
        socket.close();
        return true;
    }

    public ConnectionHandler applyInvalidPacketNotifyConsumer(Consumer<SocketAdapter> socketAdapterConsumer) {
        this.invalidPacketHandler = socketAdapterConsumer;
        return this;
    }

    public ConnectionHandler applyPacketHandler(IPacketHandler handler) {
        this.packetHandler = handler;
        return this;
    }

    public ConnectionHandler applyConnectionHandler(IConnectionHandler handler) {
        this.connectionHandler = handler;
        return this;
    }

    public boolean handleDecryption(byte[] bytes, Consumer<byte[]> dataContainerConsumer) {
        try {
            dataContainerConsumer.accept(cryptionHandler.decrypt(bytes));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ConnectionHandler startDownstreamThreads(int maxThreads) {
        for (int currentThreads = 0; currentThreads < maxThreads; currentThreads++) {
            Thread t = new Thread(() -> {

                AtomicBoolean addressAlreadyInUse = new AtomicBoolean(false);

                ServerSocket serverSocket = null;

                while (true) {
                    if(!addressAlreadyInUse.get()){
                        Socket socket = null;

                        try {
                            if(serverSocket == null)
                            serverSocket = new ServerSocket(localData.getPort());
                            socket = serverSocket.accept();
                            socket.setSoTimeout(500);
                            addressAlreadyInUse.set(true);

                            InetAddress address = socket.getInetAddress();
                            SocketAdapter socketAdapter = new SocketAdapter(address.getHostAddress(), socket.getPort());

                            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                            // Check for connection Handler acceptage (TODO: Firewall Implementation)
                            if(!connectionHandler.handle(socketAdapter, inputStream)){
                                socket.close();
                                invalidPacketHandler.accept(socketAdapter);
                                return;
                            }

                            int packetID = inputStream.read();
                            PacketDataContainer dataContainer = new PacketDataContainer(packetID, socket, new CopyOnWriteArrayList<>());


                            IPacket packet = byteHandler.handle(socketAdapter, dataContainer);

                            if(packet == null){
                                socket.close();
                                invalidPacketHandler.accept(socketAdapter);
                                return;
                            }

                            if (packetHandler != null) {
                                // PACKET HANDLING
                                packetHandler.handle(socketAdapter, packet);
                            }

                            addressAlreadyInUse.set(false);
                            socket.close();
                        } catch (Exception e) {
                            addressAlreadyInUse.set(false);
                            try {
                                socket.close();
                            } catch (Exception ex) {System.out.println("[API] Error occured while trying to handle error!");}
                            System.out.println("[API] Exeption occured while trying to resolve packets. (TYPE: " + e.getClass().getSimpleName() + " MSG:" + e.getMessage() + ")");
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
        return this;
    }
}
