package com.rui.economystore.api.capability;

import com.rui.economystore.api.stat.Stat;
import com.rui.economystore.api.stat.StatBase;
import com.rui.economystore.api.stat.StatStorage;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.api.stat.state.StatEconomyState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerCapability {
    @CapabilityInject(StatStorage.class)
    public static Capability<StatStorage> capability;

    private static CompoundNBT serializeNBT(StatStorage statStorage) {
        CompoundNBT compound = new CompoundNBT();

        statStorage.stateEconomyMap.forEach((stat, state) -> {
            CompoundNBT stateTag = new CompoundNBT();
            if(stat.getRegistryName().equals(Stats.ECONOMY.getRegistryName())) {
                stateTag.putFloat("economy", state.money);
                compound.put(stat.getRegistryName().toString(), stateTag);
            }
        });

        return compound;
    }

    private static void deserializeNBT(@Nonnull StatStorage statStorage, INBT nbt) {
        if (nbt instanceof CompoundNBT) {
            CompoundNBT compound = (CompoundNBT) nbt;
            for (String stat : compound.getAllKeys()) {
                ResourceLocation statLocation = new ResourceLocation(stat);
                if (!StatBase.REGISTRY.containsKey(statLocation)) continue;
                CompoundNBT stateTag = compound.getCompound(stat);
                Stat statObject = StatBase.REGISTRY.getValue(statLocation);

                statStorage.stateEconomyMap.put(statObject, new StatEconomyState(statObject, stateTag.getFloat("economy")));
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT> {
        public StatStorage statStorage = new StatStorage();
        public final LazyOptional<StatStorage> storageProperty = LazyOptional.of(() -> statStorage);

        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, final @Nullable Direction side) {
            if (cap == capability) return storageProperty.cast();
            else return LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return PlayerCapability.serializeNBT(statStorage);
        }


        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            PlayerCapability.deserializeNBT(statStorage, nbt);
        }
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(StatStorage.class, new Capability.IStorage<StatStorage>() {
            @Override
            public INBT writeNBT(Capability<StatStorage> capability, StatStorage instance, Direction side) {
                return serializeNBT(instance);
            }

            @Override
            public void readNBT(Capability<StatStorage> capability, StatStorage instance, Direction side, INBT nbt) {
                PlayerCapability.deserializeNBT(instance, nbt);
            }
        }, StatStorage::new);
    }
}
