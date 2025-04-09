package com.feliscape.nuanced_combat.networking;

import com.feliscape.nuanced_combat.client.data.StunnedEntityData;
import com.feliscape.nuanced_combat.networking.packets.AddStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NuancedCombatClientPayloadHandler { // Handle Packages on the CLIENT
    public static void handle(AddStunnedEntityPayload payload, IPayloadContext context){
        StunnedEntityData.addEntity(payload.entityId());
    }
    public static void handle(RemoveStunnedEntityPayload payload, IPayloadContext context){
        StunnedEntityData.removeEntity(payload.entityId());
    }
}
