package de.ytendx.endcryption.api.util;

import de.ytendx.endcryption.api.network.impl.def.c2s.PacketC2SOutHandshake;
import de.ytendx.endcryption.api.network.impl.def.s2c.PacketS2COutHandshakeAccept;
import de.ytendx.endcryption.api.network.io.packet.PacketRegistry;

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
    public void execute(final String title, final Thread thread) {
        threads.put(this.serializeKeyHashComponent(title, System.currentTimeMillis()), thread);
        thread.start();

        PacketRegistry registry = new PacketRegistry();
        registry.register(new PacketC2SOutHandshake(1));
        registry.register(new PacketS2COutHandshakeAccept(2));


    }

    /**
     * Creates a new cached Thread in the pool
     */
    public void execute(final Thread thread) {
        threads.put(this.serializeKeyHashComponent("Not-Titled", System.currentTimeMillis()), thread);
        thread.start();
    }

    /**
     * Stopps and removes all Threads
     */
    public void stopAndRemoveThreads() {
        threads.forEach((key, thread) -> {
            thread.stop();
            threads.remove(key);
        });
    }

    /**
     * Stopps all Threads
     */
    public void stopThreads() {
        threads.forEach((key, thread) -> {
            thread.stop();
        });
    }

    private String serializeKeyHashComponent(final String title, final long time) {
        return title + "/" + time;
    }
}

