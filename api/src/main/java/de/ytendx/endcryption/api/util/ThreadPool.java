package de.ytendx.endcryption.api.util;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadPool {

    private final ConcurrentHashMap<String, Thread> threads;

    public ThreadPool() {
        this.threads = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, Thread> getThreads() {
        return threads;
    }

    /**
     * Creates a new cached Thread in the pool with a Title
     */
    public void execute(@NotNull final String title, @NotNull final Thread thread){
        threads.put(this.serializeKeyHashComponent(title, System.currentTimeMillis()), thread);
        thread.start();
    }

    /**
     * Creates a new cached Thread in the pool
     */
    public void execute(@NotNull final Thread thread){
        threads.put(this.serializeKeyHashComponent("Not-Titled", System.currentTimeMillis()), thread);
        thread.start();
    }

    /**
     * Stopps and removes all Threads
     */
    public void stopAndRemoveThreads(){
        threads.forEach((key, thread) -> {
            thread.stop();
            threads.remove(key);
        });
    }

    /**
     * Stopps all Threads
     */
    public void stopThreads(){
        threads.forEach((key, thread) -> {
            thread.stop();
        });
    }

    @NotNull
    private String serializeKeyHashComponent(@NotNull final String title, @NotNull final long time){
        return title + "/" + time;
    }
}

