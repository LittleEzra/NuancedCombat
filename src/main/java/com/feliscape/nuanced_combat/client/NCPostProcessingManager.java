package com.feliscape.nuanced_combat.client;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class NCPostProcessingManager {
    public static final ResourceLocation DROWSY_EFFECT = NuancedCombat.location("shaders/post/drowsy.json");

    public void loadShader(ResourceLocation location){
        Minecraft.getInstance().gameRenderer.loadEffect(location);
    }

    public void removeShader() {
        Minecraft.getInstance().gameRenderer.shutdownEffect();
    }
}
