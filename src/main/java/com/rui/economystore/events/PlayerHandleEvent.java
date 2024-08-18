package com.rui.economystore.events;

import com.rui.economystore.api.capability.PlayerCapability;
import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.stat.StatBase;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.rui.economystore.api.network.PacketHandler.sendToServer;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class PlayerHandleEvent {
    @SubscribeEvent
    public static void injectCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(LibMisc.MOD_ID ,"stat_stronge"), new PlayerCapability.Provider());
        }
    }

    /**
     * When Player die, change world or Login, Sync Player Capability Data.
     */
    public static void syncPlayerData(PlayerEntity player) {
        PacketHandler.sendAllToClient((ServerPlayerEntity) player);
    }

    public static void resetPlayerData(PlayerEntity OldPlayer,PlayerEntity NewPlayer) {
        DataHelper.setPlayerEconomy(NewPlayer, Stats.ECONOMY, DataHelper.getPlayerEconomy(OldPlayer));
//        sendToServer(new StatSkillLevelSyncServerMessage(StatBase.stats.indexOf(Stats.TOGGLE_LEVEL),DataHelper.getPlayerSkillLevel(OldPlayer),true));
    }

    @SubscribeEvent
    public static void PlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        syncPlayerData(event.getPlayer());
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        syncPlayerData(event.getPlayer());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if(event.getEntity() instanceof ServerPlayerEntity && event.getOriginal() instanceof ServerPlayerEntity) {
            resetPlayerData(event.getOriginal(),(PlayerEntity) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void playerChangedWorld(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncPlayerData(event.getPlayer());
    }
}
