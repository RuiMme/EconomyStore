package com.rui.economystore.client.screens.stores.itemstore;

import com.rui.economystore.api.network.DataHelper;
import com.rui.economystore.api.network.PacketHandler;
import com.rui.economystore.api.network.messages.server.StatEconomySyncServerMessage;
import com.rui.economystore.api.stat.StatBase;
import com.rui.economystore.api.stat.Stats;
import com.rui.economystore.client.screens.stores.AbstractStoreContainer;
import com.rui.economystore.common.data.ItemStoreItem;
import com.rui.economystore.common.data.RecyclingStoreItem;
import com.rui.economystore.common.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

public class ItemStoreContainer extends AbstractStoreContainer {
    public ItemStoreContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public ItemStoreContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(id, playerInventory, IWorldPosCallable.NULL);
    }

    public ItemStoreContainer(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable) {
        super(ContainerInit.ITEM_STORE_CONTAINER.get(), id, playerInventory, worldPosCallable);
        this.addPlayerInventoryAndHotbar(playerInventory);
    }

    private void playerGetStoreItem(PlayerEntity player, ItemStack stack) {
        if (!stack.isEmpty()) {
            for (int i = 0; i < 36; ++i) {
                ItemStack itemstack = player.inventory.getItem(i);
                if (itemstack.isEmpty()) {
                    player.inventory.setItem(i, stack);
                    break;
                } else if (this.isSameItem(stack, itemstack) && itemstack.getCount() < itemstack.getMaxStackSize()) {
//                    this.playerInventory.setItem(i, );
                    itemstack.setCount(itemstack.getCount() + 1);
                    break;
                }
            }
        }
    }

    private boolean isSameItem(ItemStack p_217050_1_, ItemStack p_217050_2_) {
        return p_217050_1_.getItem() == p_217050_2_.getItem() && ItemStack.tagMatches(p_217050_1_, p_217050_2_);
    }

    public void buyItem(PlayerEntity player, int index) {
        if (ItemStoreItem.itemMap.size() > index && index != -1) {
            Item item = (Item) ItemStoreItem.itemMap.keySet().toArray()[index];
            float money = ItemStoreItem.itemMap.get(item) * DataHelper.getItemStoreMultiple();
            if ((DataHelper.getPlayerEconomy(player) - money) > 0) {
                this.playerGetStoreItem(player, item.getDefaultInstance());
                PacketHandler.sendToServer(new StatEconomySyncServerMessage(StatBase.stats.indexOf(Stats.ECONOMY), DataHelper.getPlayerEconomy(player) - money));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index >= 0 && index < 27) {
                if (!this.moveItemStackTo(itemstack1, 27, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 27 && index < 36 && !this.moveItemStackTo(itemstack1, 0, 27, false)) {
                return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(itemstack1, 0, 36, false)) {
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
