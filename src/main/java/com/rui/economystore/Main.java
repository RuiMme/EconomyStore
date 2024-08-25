package com.rui.economystore;

import com.rui.economystore.api.capability.PlayerCapability;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.client.keys.ModKeyBinding;
import com.rui.economystore.client.screens.stores.itemstore.ItemStoreScreen;
import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreScreen;
import com.rui.economystore.common.data.ItemStoreItem;
import com.rui.economystore.common.data.RecyclingStoreItem;
import com.rui.economystore.common.init.ContainerInit;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LibMisc.MOD_ID)
public class Main {
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(this.getClass());
        bus.addListener(this::CommonSetup);
        bus.addListener(this::ClientSetup);

        ContainerInit.register(bus);

        RecyclingStoreItem.init();
        ItemStoreItem.init();
    }

    public static final Logger log = LogManager.getLogger(LibMisc.MOD_ID);
    private static final Class<?>[] loadClasses = {
            Stats.class
    };
    private void CommonSetup(FMLCommonSetupEvent event) {
        try {
            for (Class<?> clz : loadClasses)
                Class.forName(clz.getName());
        } catch (ClassNotFoundException e) {
            log.warn("Cannot load classes, this may cause some issues", e);
        }

        PacketHandler.init();
        PlayerCapability.register();
    }

    private void ClientSetup(FMLClientSetupEvent event) {
        ModKeyBinding.init();
        event.enqueueWork(() -> {
//            ScreenManager.register(ContainerInit.ECONOMY_CONTAINER.get(), EconomyScreen::new);
            ScreenManager.register(ContainerInit.RECYCLING_STORE_CONTAINER.get(), RecyclingStoreScreen::new);
            ScreenManager.register(ContainerInit.ITEM_STORE_CONTAINER.get(), ItemStoreScreen::new);
        });
    }
}
