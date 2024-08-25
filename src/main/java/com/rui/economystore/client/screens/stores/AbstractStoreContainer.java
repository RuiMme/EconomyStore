package com.rui.economystore.client.screens.stores;

import com.rui.economystore.common.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nullable;

public abstract class AbstractStoreContainer extends Container {
    protected final IWorldPosCallable access;
    protected final PlayerInventory playerInventory;

    public AbstractStoreContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public AbstractStoreContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public AbstractStoreContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        this(ContainerInit.RECYCLING_STORE_CONTAINER.get(), id, playerInventory, worldPosCallable);
    }

    protected AbstractStoreContainer(@Nullable ContainerType<?> p_i50105_1_, int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(p_i50105_1_, id);
        this.access = worldPosCallable;
        this.playerInventory = playerInventory;
    }

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return this.access.evaluate((p_216960_2_, p_216960_3_) -> {
            return p_75145_1_.distanceToSqr((double) p_216960_3_.getX() + 0.5D, (double) p_216960_3_.getY() + 0.5D, (double) p_216960_3_.getZ() + 0.5D) <= 64.0D;
        }, true);
    }

    public boolean canTakeItemForPickAll(ItemStack p_94530_1_, Slot p_94530_2_) {
        return false;
    }

    protected void addPlayerInventoryAndHotbar(PlayerInventory playerInventory) {
        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
    }

    //对玩家的物品槽进行渲染
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
            }
        }
    }
    //对玩家的物品槽进行渲染
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 108 + k * 18, 142));
        }
    }
}
