package com.rui.economystore.api.network;

import com.rui.economystore.api.capability.PlayerCapability;
import com.rui.economystore.api.stat.Stat;
import com.rui.economystore.api.stat.Stats;
import net.minecraft.entity.player.PlayerEntity;

public class DataHelper {
    public static void setPlayerEconomy(PlayerEntity player, Stat stat, float money) {
        player.getCapability(PlayerCapability.capability).ifPresent(storage -> storage.stateEconomyMap.get(stat).money = money);
    }
    public static float getPlayerEconomy(PlayerEntity player, Stat stat) {
        return player.getCapability(PlayerCapability.capability).map(storage -> storage.stateEconomyMap.get(stat).money).orElse(0F);
    }

    public static float getPlayerEconomy(PlayerEntity player) {
        return player.getCapability(PlayerCapability.capability).map(storage -> storage.stateEconomyMap.get(Stats.ECONOMY).money).orElse(0F);
    }
}
