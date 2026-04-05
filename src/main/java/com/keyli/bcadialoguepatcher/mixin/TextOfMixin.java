package com.keyli.bcadialoguepatcher.mixin;

import com.keyli.bcadialoguepatcher.TranslationRegistry;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Text.class)
public abstract class TextOfMixin {
    @Inject(method = "of", at = @At("HEAD"), cancellable = true)
    private static void bcadialoguepatcher$replaceOf(String string, CallbackInfoReturnable<Text> cir) {
        TranslationRegistry.translate(string).ifPresent(translated ->
            cir.setReturnValue(MutableText.of(PlainTextContent.of(translated)))
        );
    }
}
