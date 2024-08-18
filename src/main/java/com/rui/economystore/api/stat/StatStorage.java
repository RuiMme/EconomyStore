package com.rui.economystore.api.stat;

import com.rui.economystore.api.stat.state.StatEconomyState;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class StatStorage {
    public Map<Stat, StatEconomyState> stateEconomyMap = new Object2ObjectOpenHashMap<>();

    public StatStorage() {
        for (StatBase stat : StatBase.stats) {
            ResourceLocation registerName = stat.getRegistryName();
            if(Stats.ECONOMY.getRegistryName().equals(registerName)) {
                stateEconomyMap.put(stat, new StatEconomyState(stat, 0));
            }
        }
    }
}
