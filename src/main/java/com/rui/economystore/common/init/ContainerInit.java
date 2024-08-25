package com.rui.economystore.common.init;

import com.rui.economystore.client.screens.economyscreen.EconomyContainer;
import com.rui.economystore.client.screens.stores.itemstore.ItemStoreContainer;
import com.rui.economystore.client.screens.stores.recyclingstore.RecyclingStoreContainer;
import com.rui.economystore.libs.LibMisc;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, LibMisc.MOD_ID);

    public static final RegistryObject<ContainerType<EconomyContainer>> ECONOMY_CONTAINER = registerContainerType(EconomyContainer::new, "economy_container");
    public static final RegistryObject<ContainerType<RecyclingStoreContainer>> RECYCLING_STORE_CONTAINER = registerContainerType(RecyclingStoreContainer::new, "recycling_store_container");
    public static final RegistryObject<ContainerType<ItemStoreContainer>> ITEM_STORE_CONTAINER = registerContainerType(ItemStoreContainer::new, "item_store_container");

    private static <T extends Container> RegistryObject<ContainerType<T>> registerContainerType(IContainerFactory<T> factory, String name) {
        return CONTAINER_TYPE.register(name, () -> IForgeContainerType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        CONTAINER_TYPE.register(eventBus);
    }
}
