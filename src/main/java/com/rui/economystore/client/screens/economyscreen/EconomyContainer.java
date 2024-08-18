package com.rui.economystore.client.screens.economyscreen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;


public class EconomyContainer extends Container {
    private final IWorldPosCallable access;
    public EconomyContainer(PlayerInventory playerInventory) {
        this(playerInventory,IWorldPosCallable.NULL);
    }
    public EconomyContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(playerInventory,IWorldPosCallable.NULL);
    }
    public EconomyContainer(PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(null, 0);
        this.access = worldPosCallable;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return this.access.evaluate((p_216960_2_, p_216960_3_) -> {
            return p_75145_1_.distanceToSqr((double)p_216960_3_.getX() + 0.5D, (double)p_216960_3_.getY() + 0.5D, (double)p_216960_3_.getZ() + 0.5D) <= 64.0D;
        }, true);
    }
}
