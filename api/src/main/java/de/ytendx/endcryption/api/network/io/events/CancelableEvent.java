package de.ytendx.endcryption.api.network.io.events;

public class CancelableEvent extends Event{

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
