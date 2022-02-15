package de.ytendx.endcryption.api.network.data.impl;

import de.ytendx.endcryption.api.network.data.PacketDataContainer;

import java.util.concurrent.CopyOnWriteArrayList;

public class EmptyDataContainer extends PacketDataContainer {
    public EmptyDataContainer(int packetID) {
        super(packetID, null, new CopyOnWriteArrayList<>());
    }
}
