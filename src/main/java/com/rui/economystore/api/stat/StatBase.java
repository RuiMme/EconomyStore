package com.rui.economystore.api.stat;

import com.rui.economystore.api.config.Configurable;
import com.rui.economystore.api.config.StatConfig;
import com.rui.economystore.libs.LibMisc;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class StatBase<T extends StatConfig> extends ForgeRegistryEntry<Stat> implements Stat, Configurable<T> {
    public static final IForgeRegistry<Stat> REGISTRY = new RegistryBuilder<Stat>()
            .setName(new ResourceLocation(LibMisc.MOD_ID, "stats"))
            .setType(Stat.class)
            .create();
    //    public final T config;
    public static final ObjectList<StatBase<?>> stats = new ObjectArrayList<>(16);
    public StatBase(String key) {
        stats.add(this);
        setRegistryName(LibMisc.MOD_ID, key.toLowerCase());
        REGISTRY.register(this);
    }

    @Override
    public T createConfig(ForgeConfigSpec.Builder builder) {
        return null;
    }
}
