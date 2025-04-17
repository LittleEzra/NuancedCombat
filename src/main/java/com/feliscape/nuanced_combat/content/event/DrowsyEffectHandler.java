package com.feliscape.nuanced_combat.content.event;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.networking.packets.AddStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.UpdateDrowsyShaderPayload;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.network.PacketDistributor;

//@EventBusSubscriber(modid = NuancedCombat.MOD_ID)
public class DrowsyEffectHandler {
    /*@SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.hasEffect(NuancedCombatMobEffects.DROWSY)){
            PacketDistributor.sendToPlayer(serverPlayer, new UpdateDrowsyShaderPayload(false));
        }
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getEffectInstance().is(NuancedCombatMobEffects.DROWSY)){
            PacketDistributor.sendToPlayer(serverPlayer, new UpdateDrowsyShaderPayload(false));
        }
    }
    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getEffectInstance().is(NuancedCombatMobEffects.DROWSY)){
            PacketDistributor.sendToPlayer(serverPlayer, new UpdateDrowsyShaderPayload(false));
        }
    }
    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event){
        if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getEffectInstance().is(NuancedCombatMobEffects.DROWSY)){
            PacketDistributor.sendToPlayer(serverPlayer, new UpdateDrowsyShaderPayload(false));
        }
    }*/
}
