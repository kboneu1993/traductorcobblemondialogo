package com.keyli.bcadialoguepatcher;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BcaDialoguePatcherClient implements ClientModInitializer {
    public static final String MOD_ID = "bcadialoguepatcher";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        var configDir = FabricLoader.getInstance().getConfigDir();
        var path = configDir.resolve("bcadialoguepatcher").resolve("translations.json");
        TranslationRegistry.load(path);
        LOGGER.info("BCA Dialogue Patcher cargado con {} reemplazos.", TranslationRegistry.size());
    }
}
