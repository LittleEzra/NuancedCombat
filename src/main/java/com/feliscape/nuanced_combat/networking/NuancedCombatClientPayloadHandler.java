package com.feliscape.nuanced_combat.networking;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.NCPostProcessingManager;
import com.feliscape.nuanced_combat.client.NuancedCombatClient;
import com.feliscape.nuanced_combat.client.data.StunnedEntityData;
import com.feliscape.nuanced_combat.networking.packets.AddStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.UpdateDrowsyShaderPayload;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NuancedCombatClientPayloadHandler { // Handle Packages on the CLIENT
    public static void handle(AddStunnedEntityPayload payload, IPayloadContext context){
        StunnedEntityData.addEntity(payload.entityId());
    }
    public static void handle(RemoveStunnedEntityPayload payload, IPayloadContext context){
        StunnedEntityData.removeEntity(payload.entityId());
    }
    public static void handle(UpdateDrowsyShaderPayload payload, IPayloadContext context){
        if (context.player().hasEffect(NuancedCombatMobEffects.DROWSY)){
            NuancedCombat.LOGGER.debug("Player has effect: Drowsy");
            NuancedCombatClient.getInstance().postProcessingManager.loadShader(NCPostProcessingManager.DROWSY_EFFECT);
        } else{
            NuancedCombatClient.getInstance().postProcessingManager.removeShader();
        }
    }
}
