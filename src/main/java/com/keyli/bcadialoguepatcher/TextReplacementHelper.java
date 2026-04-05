package com.keyli.bcadialoguepatcher;

import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public final class TextReplacementHelper {
    private TextReplacementHelper() {}

    public static Text replaceText(Text original) {
        if (original == null) {
            return null;
        }

        String plain = original.getString();
        var translated = TranslationRegistry.translate(plain);
        if (translated.isPresent()) {
            MutableText out = Text.literal(translated.get());
            Style style = original.getStyle();
            if (style != null) {
                out.setStyle(style);
            }
            return out;
        }
        return original;
    }
}
