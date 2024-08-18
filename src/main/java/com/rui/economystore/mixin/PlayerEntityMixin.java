package com.rui.economystore.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Unique
    private static final DataParameter<Float> DATA_PLAYER_ECONOMY = EntityDataManager.defineId(PlayerEntityMixin.class, DataSerializers.FLOAT);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    public void defineSynchedDataMixin(CallbackInfo callbackInfo) {
        System.out.println("is run here:"+this.entityData.get(DATA_PLAYER_ECONOMY));
        this.entityData.define(DATA_PLAYER_ECONOMY, 500.0F);
    }
}
