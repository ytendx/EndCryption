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
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
            IPacketDataContainer decryptedContainer = new EmptyDataContainer();
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
                while (true) {
                    try {
                        final ServerSocket serverSocket = new ServerSocket(localData.getPort());
                        final Socket socket = serverSocket.accept();
                        final InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        String content = bufferedReader.readLine();
                        InetAddress address = ((InetSocketAddress) socket.getChannel().getRemoteAddress()).getAddress();
                        SocketAdapter socketAdapter = new SocketAdapter(address.getHostAddress(), socket.getPort());

                        if (!content.contains(EndCryption.PACKET_DATA_SPLITTER)) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            return;
                        }

                        if (!connectionHandler.handle(socketAdapter, content)) {
                            if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                            socket.close();
                            return;
                        }

                        IPacketDataContainer dataContainer = new EmptyDataContainer();
                        for (String packetData : content.split(EndCryption.PACKET_DATA_SPLITTER)) {
                            dataContainer.getPacketData().add(packetData.getBytes());
                        }

                        handleDecryption(dataContainer, dataContainer1 -> {
                            IPacket packet = byteHandler.handle(socketAdapter, dataContainer1);

                            if (packet == null) {
                                if (invalidPacketHandler != null) invalidPacketHandler.accept(socketAdapter);
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return;
                            }

                            if (packetHandler != null) {
                                // PACKET HANDLING
                                packetHandler.handle(socketAdapter, packet);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return this;
    }
}
