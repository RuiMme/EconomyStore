package com.rui.economystore.client.screens.stores.recyclingstore;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.network.messages.server.StatEconomySyncServerMessage;
import com.rui.economystore.api.stat.StatBase;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.client.screens.inventory.StoreInventory;
import com.rui.economystore.client.screens.stores.AbstractStoreContainer;
import com.rui.economystore.common.data.RecyclingStoreItem;
import com.rui.economystore.common.init.ContainerInit;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

public class RecyclingStoreContainer extends AbstractStoreContainer {
    private final StoreInventory storeInventory;

    public RecyclingStoreContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public RecyclingStoreContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public RecyclingStoreContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(ContainerInit.RECYCLING_STORE_CONTAINER.get(), id, playerInventory, worldPosCallable);

        this.storeInventory = new StoreInventory();
        this.addSlot(new Slot(this.storeInventory, 0, 162, 37));
        this.addPlayerInventoryAndHotbar(playerInventory);
    }

    public void tryMoveItems(int p_217046_1_) {
        if (RecyclingStoreItem.itemMap.size() > p_217046_1_) {
            ItemStack itemstack = this.storeInventory.getItem(0);
            if (!itemstack.isEmpty()) {
                if (!this.moveItemStackTo(itemstack, 1, 37, true)) {
                    return;
                }
                this.storeInventory.setItem(0, itemstack);
            }

            if (this.storeInventory.getItem(0).isEmpty()) {
                ItemStack itemstack2 = ((Item) RecyclingStoreItem.itemMap.keySet().toArray()[p_217046_1_]).getDefaultInstance();
                this.moveFromInventoryToStoreSlot(0, itemstack2);
            }

        }
    }

    private void moveFromInventoryToStoreSlot(int p_217053_1_, ItemStack p_217053_2_) {
        if (!p_217053_2_.isEmpty()) {
            for(int i = 1; i < 37; ++i) {
                ItemStack itemstack = this.slots.get(i).getItem();
                if (!itemstack.isEmpty() && this.isSameItem(p_217053_2_, itemstack)) {
                    ItemStack itemstack1 = this.storeInventory.getItem(p_217053_1_);
                    int j = itemstack1.isEmpty() ? 0 : itemstack1.getCount();
                    int k = Math.min(p_217053_2_.getMaxStackSize() - j, itemstack.getCount());
                    ItemStack itemstack2 = itemstack.copy();
                    int l = j + k;
                    itemstack.shrink(k);
                    itemstack2.setCount(l);
                    this.storeInventory.setItem(p_217053_1_, itemstack2);
                    if (l >= p_217053_2_.getMaxStackSize()) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isSameItem(ItemStack p_217050_1_, ItemStack p_217050_2_) {
        return p_217050_1_.getItem() == p_217050_2_.getItem() && ItemStack.tagMatches(p_217050_1_, p_217050_2_);
    }

    private void sellItems() {
        this.storeInventory.removeItemNoUpdate(0);
    }

    public void sellItemAndGetMoney(PlayerEntity player) {
        if (!this.storeInventory.getItem(0).isEmpty()) {
            ItemStack stack = this.storeInventory.getItem(0);
            Item item = stack.getItem();
            float money = RecyclingStoreItem.getItemMapValue(item)*stack.getCount();
            this.sellItems();
            if(player instanceof ClientPlayerEntity) {
                PacketHandler.sendToServer(new StatEconomySyncServerMessage(StatBase.stats.indexOf(Stats.ECONOMY), DataHelper.getPlayerEconomy(player)+money));
            }
        }
    }

    public void removed(PlayerEntity p_75134_1_) {
        super.removed(p_75134_1_);
        if (!p_75134_1_.level.isClientSide) {
            if (!p_75134_1_.isAlive() || p_75134_1_ instanceof ServerPlayerEntity && ((ServerPlayerEntity)p_75134_1_).hasDisconnected()) {
                ItemStack itemstack = this.storeInventory.removeItemNoUpdate(0);
                if (!itemstack.isEmpty()) {
                    p_75134_1_.drop(itemstack, false);
                }
            } else {
                p_75134_1_.inventory.placeItemBackInInventory(p_75134_1_.level, this.storeInventory.removeItemNoUpdate(0));
            }
        }
    }

    public void slotsChanged(IInventory p_75130_1_) {
        this.storeInventory.updateSlotItem();
        super.slotsChanged(p_75130_1_);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (index >= 1 && index < 28) {
                    if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 28 && index < 37 && !this.moveItemStackTo(itemstack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }
}
