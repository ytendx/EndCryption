package de.ytendx.endcryption.api.network.data.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EmptyDataContainer extends AbstractPacketDataContainer{
    public EmptyDataContainer() {
        super(new CopyOnWriteArrayList<>());
    }
}
