package com.rui.economystore.libs;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class LibDataToTextComponent {
    public static ITextComponent toComponent(Object object) {
        return new TranslationTextComponent(object.toString());
    }
    public static ITextComponent toComponent(Object object, TextFormatting formatting) {
        return new TranslationTextComponent(object.toString()).withStyle(formatting);
    }
}
