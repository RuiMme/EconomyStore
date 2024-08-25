package com.rui.economystore.events;

import com.rui.economystore.client.keys.ModKeyBinding;
import com.rui.economystore.client.screens.economyscreen.EconomyScreen;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, value = Dist.CLIENT)
public class ModGuiEvent {

//    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void Keyboard(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        if(player==null) return;
        if(ModKeyBinding.ECONOMY_MENU.consumeClick()) {
            setScreen(mc, new EconomyScreen(player));
        }
    }

    public static void setScreen(Minecraft minecraft, Screen screen) {
        if(minecraft.isPaused()) return;
        if(minecraft.screen != null) return;
        minecraft.setScreen(screen);
    }
}
