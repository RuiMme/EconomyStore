package com.rui.economystore.common.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Set;

public class RecyclingStoreItem {
    public static final HashMap<Item, Float> itemMap = new HashMap<>();

    public static void init() {
        // Block
        itemMap.put(Items.DIRT, 0.1F);
        itemMap.put(Items.STONE, 0.1F);
        itemMap.put(Items.COBBLESTONE, 0.01F);

        // Ingot
        itemMap.put(Items.COAL, 0.1F);
        itemMap.put(Items.IRON_INGOT, 1F);
        itemMap.put(Items.REDSTONE, 0.1F);
        itemMap.put(Items.LAPIS_LAZULI, 0.4F);
        itemMap.put(Items.GOLD_INGOT, 10F);
        itemMap.put(Items.EMERALD, 10F);
        itemMap.put(Items.DIAMOND, 15F);
        itemMap.put(Items.NETHER_SPROUTS, 15F);
        itemMap.put(Items.NETHERITE_INGOT, 100F);

        // Food
        itemMap.put(Items.CHICKEN, 0.5F);
        itemMap.put(Items.PORKCHOP, 0.7F);
        itemMap.put(Items.MUTTON, 0.7F);
        itemMap.put(Items.BEEF, 1F);
        itemMap.put(Items.RABBIT, 5F);
        itemMap.put(Items.COD, 0.5F);
        itemMap.put(Items.SALMON, 0.5F);
        itemMap.put(Items.TROPICAL_FISH, 1F);
        itemMap.put(Items.PUFFERFISH, 5F);

        // Misc
        itemMap.put(Items.RABBIT_HIDE, 5F);
        itemMap.put(Items.RABBIT_FOOT, 10F);

        // Special
        itemMap.put(Items.DRAGON_EGG, 10000F);
    }

    public static float getItemMapValue(Item index) {
        return itemMap.get(index);
    }

    public static Set<Item> getItemMapKeys() {
        return RecyclingStoreItem.itemMap.keySet();
    }
}
