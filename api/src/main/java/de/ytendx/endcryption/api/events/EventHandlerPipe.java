package de.ytendx.endcryption.api.events;

import de.ytendx.endcryption.api.events.impl.CancelableEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventHandlerPipe {

    private ConcurrentHashMap<Class<? extends Event>, ListenerCallable> pipe;

    public EventHandlerPipe() {
        pipe = new ConcurrentHashMap<>();
    }

    public void register(Class<? extends Event> eventType, ListenerCallable callable) {
        if (pipe != null && !pipe.contains(callable)) {
            pipe.put(eventType, callable);
            // TODO Log errors and Information
        }
    }

    public void unregister(Class<? extends Event> eventType, ListenerCallable callable) {
        if (pipe != null && pipe.containsKey(eventType) && pipe.contains(callable)) {
            pipe.remove(eventType, callable);
            // TODO Log errors and Information
        }
    }

    public <G extends Event> void call(G event) {
        for(Class<? extends Event> eventClasses : pipe.keySet()){
            if(eventClasses.isInstance(event.getClass()))
            pipe.get(eventClasses).call(event);
        }
    }

    public <G extends CancelableEvent> void call(G event, Consumer<Boolean> callback) {
        boolean cancel = false;
        for(Class<? extends Event> eventClasses : pipe.keySet()){
            if(!eventClasses.isInstance(event.getClass())) continue;
            pipe.get(eventClasses).call(event);
            if(event.isCancelled()) cancel = true;
        }
        callback.accept(cancel);
    }
}
