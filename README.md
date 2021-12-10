# EndCryption

**EndCryption is used for Anonymous Hosting dependent privacy data connections.**

_________________

### API Instructions

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

So now that you have a complete ConnectionHandler you have do make you a class with a PacketRegistry.
So let´s do this:

```java
@Getter
@AllArgsConstructor
public class YourEndCryptionClass {

    private PacketRegistry packetRegistry;
    private ConnectionHandler connectionHandler;
    
    public void register(){
        final IPacket packet = new PacketC2SOutHandshake(1); // Creates a new PacketC2SOutHandshake Object with the packetID 1
        packetRegistry.register(packet); // Registers the packet in our packet registry
    }
    
    public IPacket getPacketByID(int id){
        return packetRegistry.getPacketByID(id); // Gets the packet from the given packet id (The packet id is a attribute from the IPacket)
    }

}
```

You may also use the EventAPI to create Events for yourself. Here is a quick example:

Let´s start with creating a Event class:

```java
@Setter @Getter @AllArgsConstructor
public class MyEvent extends Event {
    private String packetData;
}
```

So now we have a event class we want to register a Listener and call the Event.

```java
EventHandlerPipe pipe = new EventHandlerPipe(); // Initialization of an EventHandlerPipe

pipe.register(MyEvent.class /* The Event you want to listen to */, event -> {
    // TODO Your turn, handle the Event :)
});

pipe.call(new MyEvent()); // Calls the MyEvent Listeners
```
