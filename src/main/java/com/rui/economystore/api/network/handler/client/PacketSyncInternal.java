package com.rui.economystore.api.network.handler.client;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.messages.client.StatEconomySyncClientMessage;
import com.rui.economystore.api.stat.StatBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PacketSyncInternal {
    @OnlyIn(Dist.CLIENT)
    public static void acceptEconomySyncInternal(StatEconomySyncClientMessage message) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null)
            return;
        StatBase stat = StatBase.stats.get(message.stat);
        DataHelper.setPlayerEconomy(player,stat,message.money);
    }
}
