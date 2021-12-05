package de.ytendx.endcryption.api.events;

public interface ListenerCallable<T extends Event> {

    void call(T event);

}
