package com.rui.economystore.events;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.wrolddata.ResetItemStoreMultipleSavedData;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
import java.util.Random;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class WorldHandleEvent {
    private static final int resetAmountTime = 24000;
    private static final Random random = new Random();
    private static final DecimalFormat df = new DecimalFormat("#.00");
    @SubscribeEvent
    public static void WorldEvent(TickEvent.WorldTickEvent event) {
        if(event.world instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.world;
            if(DataHelper.getResetItemStoreMultipleSavedData(world.getServer()).getMultiple() == 0.0F || world.getGameTime() % resetAmountTime == 0) {
                float multiple = Float.parseFloat(df.format(random.nextFloat()+0.5F));
                DataHelper.setItemStoreMultiple(world.getServer(), multiple);
            }
            if(DataHelper.getItemStoreMultiple() == 0.0F) {
                DataHelper.setItemStoreMultiple(DataHelper.getResetItemStoreMultipleSavedData(world.getServer()).getMultiple());
            }
        }
    }
}
