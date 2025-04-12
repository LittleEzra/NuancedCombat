package com.feliscape.nuanced_combat.networking;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.networking.packets.AddStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.UpdateDrowsyShaderPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handlers.ClientPayloadHandler;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = NuancedCombat.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NuancedCombatPayloads {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                AddStunnedEntityPayload.TYPE,
                AddStunnedEntityPayload.STREAM_CODEC,
                NuancedCombatClientPayloadHandler::handle
        );
        registrar.playToClient(
                RemoveStunnedEntityPayload.TYPE,
                RemoveStunnedEntityPayload.STREAM_CODEC,
                NuancedCombatClientPayloadHandler::handle
        );
        registrar.playToClient(
                UpdateDrowsyShaderPayload.TYPE,
                UpdateDrowsyShaderPayload.STREAM_CODEC,
                NuancedCombatClientPayloadHandler::handle
        );
    }
}
