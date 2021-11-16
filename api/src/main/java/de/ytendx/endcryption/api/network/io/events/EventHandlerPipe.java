package de.ytendx.endcryption.api.network.io.events;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class EventHandlerPipe {

    private ConcurrentHashMap<Class<? extends Event>, ListenerCallable<? extends Event>> pipe;

    public EventHandlerPipe(){
        pipe = new ConcurrentHashMap<>();
    }

    public void register(Class<? extends Event> eventType, ListenerCallable<? extends Event> callable){
        if(pipe != null && !pipe.contains(callable)){
            pipe.put(eventType, callable);
            // TODO Log errors and Information
        }
    }

    public void unregister(Class<? extends Event> eventType, ListenerCallable<? extends Event> callable){
        if(pipe != null && pipe.containsKey(eventType) && pipe.contains(callable)){
            pipe.remove(eventType, callable);
            // TODO Log errors and Information
        }
    }

    public void call(Class<? extends Event> clazz, Runnable callback){

    }
}
