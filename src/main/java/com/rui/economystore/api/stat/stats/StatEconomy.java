package com.rui.economystore.api.stat.stats;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.stat.StatBase;
import net.minecraft.entity.player.PlayerEntity;

public class StatEconomy extends StatBase {
    public StatEconomy(String key) {
        super(key);
    }

    public float getPlayerXp(PlayerEntity player) {
        return DataHelper.getPlayerEconomy(player, this);
    }
}
