package de.ytendx.endcryption.api.network.io.events;

public interface ListenerCallable<T extends Event> {

    void call(T event);

}
