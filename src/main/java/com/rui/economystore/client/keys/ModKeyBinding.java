package com.rui.economystore.client.keys;

import com.rui.economystore.libs.LibMisc;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModKeyBinding extends KeyBinding {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();

    public static final KeyBinding ECONOMY_MENU = new ModKeyBinding("activate_economy_menu", KeyConflictContext.IN_GAME, KeyModifier.NONE, 82, "key.categories.economystore");
    public ModKeyBinding(String description, IKeyConflictContext keyConflictContext, KeyModifier keyModifier, int keyCode, String category) {
        super(String.format("key.%s.%s", LibMisc.MOD_ID, description), keyConflictContext, keyModifier, InputMappings.Type.KEYSYM, keyCode, category);
        KEY_BINDINGS.add(this);
    }

    public static void init() {
        for(KeyBinding binding : KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(binding);
        }
    }
}
