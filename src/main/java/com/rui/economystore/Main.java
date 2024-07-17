package com.rui.economystore;

import com.rui.economystore.libs.LibMisc;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibMisc.MOD_ID)
public class Main {
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this.getClass());
        bus.addListener(this::CommonSetup);
        bus.addListener(this::ClientSetup);
    }

    private void CommonSetup(FMLCommonSetupEvent event) {

    }

    private void ClientSetup(FMLClientSetupEvent event) {

    }
}
