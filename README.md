# EndCryption

EndCryption is used for Anonymous Hosting dependent privacy data connections.

Here you can see a quick instruction how to use the API.

```java
// The connection handler provides the pipeline for your connections. The SocketAdapter provides you
// the containment of the local pingable configuration. (How other´s can ping you)
ConnectionHandler connectionHandler = new ConnectionHandler(new SocketAdapter("localhost", 4044),
        (adapter1, dataContainer) -> {
            // Your turn! Insert your packet decoding.
            return null;
        },
        new CryptionHandler(-1, aLong -> {})); // This is the End-to-End Cryption handler.
```

If you want to add a packet handler to the pipeline just go for this:

```java
connectionHandler.applyPacketHandler((adapter1, packet) -> {
    if(packet.encodeUnserializedData().getPacketDataSize() > 0)
        System.out.println("I have handled a packet! WUHUUUU");
    return true;
});
```

