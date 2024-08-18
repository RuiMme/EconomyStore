package com.rui.economystore.api.network.messages.client;

import com.rui.economystore.api.network.messages.IMessage;
import net.minecraft.network.PacketBuffer;

public class StatEconomySyncClientMessage implements IMessage {
    public int stat;
    public float money;
    public StatEconomySyncClientMessage(){

    }
    public StatEconomySyncClientMessage(int stat, float money) {
        this.stat = stat;
        this.money = money;
    }
    @Override
    public void readBytes(PacketBuffer buf) {
        stat = buf.readInt();
        money = buf.readFloat();
    }

    @Override
    public void writeBytes(PacketBuffer buf) {
        buf.writeInt(stat);
        buf.writeFloat(money);
    }
}
