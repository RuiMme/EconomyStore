package com.rui.economystore.api.network.messages;

import net.minecraft.network.PacketBuffer;

public interface IMessage {
    void readBytes(PacketBuffer buf);
    void writeBytes(PacketBuffer buf);
}
