package me.mizukiyuu.customsolidsky.client;

import me.mizukiyuu.customsolidsky.command.CommandRegister;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomsolidskyClient implements ClientModInitializer {
    public static final String MOD_ID = "customsolidsky";
    public static final String MOD_NAME = "CustomSolidSky";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static final SkyColorSetting SKY_OPTIONS = new SkyColorSetting();

    @Override
    public void onInitializeClient() {
        CommandRegister.register();
    }
}
