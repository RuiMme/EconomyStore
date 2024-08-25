package com.rui.economystore.common.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class ItemStoreItem {
    public static final HashMap<Item, Float> itemMap = new HashMap<>();
    public static void init() {
        itemMap.put(Items.IRON_PICKAXE, 6F);
        itemMap.put(Items.IRON_AXE, 6F);
        itemMap.put(Items.IRON_HOE, 4F);
        itemMap.put(Items.IRON_SWORD, 4F);
        itemMap.put(Items.IRON_SHOVEL, 2F);
    }

    public static float getItemMapValue(Item index) {
        return itemMap.get(index);
    }
}
