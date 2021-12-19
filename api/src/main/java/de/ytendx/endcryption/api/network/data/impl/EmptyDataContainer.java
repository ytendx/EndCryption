package de.ytendx.endcryption.api.network.data.impl;

import java.util.concurrent.CopyOnWriteArrayList;

public class EmptyDataContainer extends AbstractPacketDataContainer {
    public EmptyDataContainer(int packetID) {
        super(packetID, new CopyOnWriteArrayList<>());
    }
}
