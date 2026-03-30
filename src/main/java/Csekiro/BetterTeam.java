package Csekiro;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BetterTeam implements ModInitializer {
    public static final String MOD_ID = "betterteam";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[BetterTeam] Initialized.");
    }
}