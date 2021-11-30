package de.ytendx.endcryption.api.network.io.events.impl;

import de.ytendx.endcryption.api.network.io.events.Event;

public class CancelableEvent extends Event {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
