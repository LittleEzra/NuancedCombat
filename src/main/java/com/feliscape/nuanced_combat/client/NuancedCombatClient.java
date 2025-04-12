package com.feliscape.nuanced_combat.client;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.render.WispRenderer;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class NuancedCombatClient {
    private static NuancedCombatClient instance;

    private final WispRenderer wispRenderer;

    public final NCPostProcessingManager postProcessingManager;

    public NuancedCombatClient(RegisterClientReloadListenersEvent event){
        instance = this;
        NuancedCombat.LOGGER.info("NuancedCombatClient instantiated");

        wispRenderer = new WispRenderer(Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(wispRenderer);
        postProcessingManager = new NCPostProcessingManager();
    }

    public WispRenderer wispRenderer() { return this.wispRenderer; }

    public static NuancedCombatClient getInstance() {
        return instance;
    }
}
