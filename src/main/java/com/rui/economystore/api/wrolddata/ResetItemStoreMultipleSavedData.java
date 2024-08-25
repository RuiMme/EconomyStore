package com.rui.economystore.api.wrolddata;

import com.rui.economystore.libs.LibMisc;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldSavedData;

public class ResetItemStoreMultipleSavedData extends WorldSavedData {
    private static final String DATA_NAME = LibMisc.MOD_ID + "_resetitemstoremultiple";
    private float multiple;
    public ResetItemStoreMultipleSavedData() {
        super(DATA_NAME);
    }

    public static ResetItemStoreMultipleSavedData create() {
        return new ResetItemStoreMultipleSavedData();
    }

    @Override
    public void load(CompoundNBT p_76184_1_) {
        this.multiple = p_76184_1_.getFloat("multiple");
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189551_1_) {
        p_189551_1_.putFloat("multiple", this.multiple);
        return p_189551_1_;
    }

    public static ResetItemStoreMultipleSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(ResetItemStoreMultipleSavedData::create, DATA_NAME);
    }

    public float getMultiple() {
        return this.multiple;
    }

    public void setMultiple(float multiple) {
        this.multiple = multiple;
        this.setDirty();
    }
}
