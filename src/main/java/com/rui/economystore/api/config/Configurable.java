package com.rui.economystore.api.config;

import net.minecraftforge.common.ForgeConfigSpec;

public interface Configurable<T> {
    T createConfig(ForgeConfigSpec.Builder builder);
}
