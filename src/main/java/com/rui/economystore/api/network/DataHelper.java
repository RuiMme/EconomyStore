package com.rui.economystore.api.network;

import com.rui.economystore.api.capability.PlayerCapability;
import com.rui.economystore.api.stat.Stat;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.api.wrolddata.ResetItemStoreMultipleSavedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

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

    private static float itemStoreMultiple = 0.0F;
    public static void setItemStoreMultiple(MinecraftServer server, float multiple) {
        ResetItemStoreMultipleSavedData.get(server).setMultiple(multiple);
        setItemStoreMultiple(DataHelper.getResetItemStoreMultipleSavedData(server).getMultiple());
    }
    public static void setItemStoreMultiple(ServerPlayerEntity player, float multiple) {
        setItemStoreMultiple(player.server,multiple);
    }
    public static ResetItemStoreMultipleSavedData getResetItemStoreMultipleSavedData(MinecraftServer server) {
        return ResetItemStoreMultipleSavedData.get(server);
    }
    public static ResetItemStoreMultipleSavedData getResetItemStoreMultipleSavedData(ServerPlayerEntity player) {
        return getResetItemStoreMultipleSavedData(player.server);
    }

    public static void setItemStoreMultiple(float multiple) {
        itemStoreMultiple = multiple;
    }
    public static float getItemStoreMultiple() {
        return itemStoreMultiple;
    }
}
