package de.ytendx.endcryption.api.events.impl;

import de.ytendx.endcryption.api.events.Event;

public class CancelableEvent extends Event {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
