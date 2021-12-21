package de.ytendx.endcryption.api.network.io;

import de.ytendx.endcryption.api.EndCryption;
import de.ytendx.endcryption.api.encryption.CryptionHandler;
import de.ytendx.endcryption.api.network.IPacket;
import de.ytendx.endcryption.api.network.data.IPacketDataContainer;
import de.ytendx.endcryption.api.network.data.impl.EmptyDataContainer;
import de.ytendx.endcryption.api.network.io.handler.IByteHandler;
import de.ytendx.endcryption.api.network.io.handler.IConnectionHandler;
import de.ytendx.endcryption.api.network.io.handler.IPacketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
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

    public boolean sendPacketData(SocketAdapter adapter, IPacketDataContainer dataContainer) throws IOException {
        final Socket socket = new Socket(adapter.getIp(), adapter.getPort());
        final PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.write(dataContainer.getPacketID());
        writer.write(dataContainer.serialize());
        writer.flush();
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

    public boolean handleDecryption(IPacketDataContainer dataContainer, Consumer<IPacketDataContainer> dataContainerConsumer) {
        try {
            IPacketDataContainer decryptedContainer = new EmptyDataContainer(dataContainer.getPacketID());
            for (byte[] array : dataContainer.getPacketData()) {
                decryptedContainer.getPacketData().add(this.cryptionHandler.decrypt(array));
            }
            dataContainerConsumer.accept(decryptedContainer);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ConnectionHandler startDownstreamThreads(int maxThreads) {
        for (int currentThreads = 0; currentThreads < maxThreads; currentThreads++) {
            new Thread(() -> {

                AtomicBoolean addressAlreadyInUse = new AtomicBoolean(false);

                while (true) {

                    if(addressAlreadyInUse.get()) continue;

                    ServerSocket serverSocket = null;
                    Socket socket = null;

                    try {
                        serverSocket = new ServerSocket(localData.getPort());
                        socket = serverSocket.accept();
                        final InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        socket.setSoTimeout(500);

                        addressAlreadyInUse.set(true);

                        int packetID = bufferedReader.read();
                        String content = bufferedReader.readLine();
                        InetAddress address = socket.getInetAddress();
                        SocketAdapter socketAdapter = new SocketAdapter(address.getHostAddress(), socket.getPort());

                        if(content == null) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            serverSocket.close();
                            addressAlreadyInUse.set(false);
                            continue;
                        }

                        if (!content.contains(EndCryption.PACKET_DATA_SPLITTER) || packetID == 0 || content.isEmpty()) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            serverSocket.close();
                            addressAlreadyInUse.set(false);
                            continue;
                        }

                        if (connectionHandler != null && !connectionHandler.handle(socketAdapter, content)) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            serverSocket.close();
                            addressAlreadyInUse.set(false);
                            continue;
                        }

                        System.out.println("PacketID1: " + packetID);
                        System.out.println("Content1: " + content);

                        IPacketDataContainer dataContainer = new EmptyDataContainer(packetID);
                        for (String packetData : content.split(EndCryption.PACKET_DATA_SPLITTER)) {
                            dataContainer.getPacketData().add(packetData.getBytes());
                        }

                        System.out.println("PacketID2: " + dataContainer.getPacketID());
                        System.out.println("Content2: " + dataContainer.serialize());

                        IPacket packet = byteHandler.handle(socketAdapter, dataContainer);

                        if (packet == null) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            serverSocket.close();
                            addressAlreadyInUse.set(false);
                            continue;
                        }

                        System.out.println("PacketID3: " + packet.getPacketID());
                        System.out.println("Content3: " + packet.encodeUnserializedData().serialize());

                        if (packetHandler != null) {
                            // PACKET HANDLING
                            packetHandler.handle(socketAdapter, packet);
                        }

                        addressAlreadyInUse.set(false);
                        socket.close();
                        serverSocket.close();
                    } catch (Exception e) {
                        addressAlreadyInUse.set(false);
                        try {
                            socket.close();
                            serverSocket.close();
                        } catch (Exception ex) {System.out.println("[API] Error occured while trying to handle error!");}
                        System.out.println("[API] Exeption occured while trying to resolve packets. (TYPE: " + e.getClass().getSimpleName() + " MSG:" + e.getMessage() + ")");
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return this;
    }
}
