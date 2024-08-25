package com.rui.economystore.client.screens.economyscreen;

import com.rui.economystore.common.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;


public class EconomyContainer extends Container {
    private final IWorldPosCallable access;
    public EconomyContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory,IWorldPosCallable.NULL);
    }
    public EconomyContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(id, playerInventory,IWorldPosCallable.NULL);
    }
    public EconomyContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(ContainerInit.ECONOMY_CONTAINER.get(), id);
        this.access = worldPosCallable;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return this.access.evaluate((p_216960_2_, p_216960_3_) -> {
            return p_75145_1_.distanceToSqr((double)p_216960_3_.getX() + 0.5D, (double)p_216960_3_.getY() + 0.5D, (double)p_216960_3_.getZ() + 0.5D) <= 64.0D;
        }, true);
    }
}
