package com.feliscape.nuanced_combat.content.event;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import com.feliscape.nuanced_combat.networking.packets.AddStunnedEntityPayload;
import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import com.feliscape.nuanced_combat.registry.NuancedCombatDataAttachments;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import com.feliscape.nuanced_combat.registry.NuancedCombatPotions;
import com.feliscape.nuanced_combat.registry.NuancedCombatTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.brewing.PotionBrewEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.EffectParticleModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class Events {
    @EventBusSubscriber(modid = NuancedCombat.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    static class GameEvents {

        @SubscribeEvent
        public static void onLivingShieldBlock(LivingShieldBlockEvent event){
            if (event.getBlocked() && event.getDamageSource().getDirectEntity() instanceof LivingEntity livingAttacker &&
                    event.getEntity().getUseItem().canPerformAction(ItemAbilities.SHIELD_BLOCK) && !livingAttacker.getType().is(NuancedCombatTags.EntityTypes.STUN_IMMUNE)){
                var lookup = event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                int level = event.getEntity().getUseItem().getEnchantmentLevel(lookup.getOrThrow(NuancedCombatEnchantments.STUNNING));
                if (level > 0){
                    int duration = 20 + 10 * (level);
                    if (livingAttacker instanceof Player) duration /= 2;

                    livingAttacker.addEffect(new MobEffectInstance(NuancedCombatMobEffects.STUN, duration));
                    event.setCanceled(true);
                }
            }
        }
        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event){
            if (!(event.getEntity() instanceof Player) && event.getEntity() instanceof LivingEntity living && living.hasEffect(NuancedCombatMobEffects.STUN)){
                PacketDistributor.sendToAllPlayers(new AddStunnedEntityPayload(event.getEntity().getId()));
            }
        }

        @SubscribeEvent
        public static void onEffectAdded(MobEffectEvent.Added event){
            if (!(event.getEntity() instanceof Player) && event.getEffectInstance().is(NuancedCombatMobEffects.STUN)){
                PacketDistributor.sendToAllPlayers(new AddStunnedEntityPayload(event.getEntity().getId()));
            }
        }
        @SubscribeEvent
        public static void onEffectRemoved(MobEffectEvent.Remove event){
            if (!(event.getEntity() instanceof Player) && event.getEffectInstance().is(NuancedCombatMobEffects.STUN)){
                PacketDistributor.sendToAllPlayers(new RemoveStunnedEntityPayload(event.getEntity().getId()));
            }
        }
        @SubscribeEvent
        public static void onEffectExpired(MobEffectEvent.Expired event){
            if (!(event.getEntity() instanceof Player) && event.getEffectInstance().is(NuancedCombatMobEffects.STUN)){
                PacketDistributor.sendToAllPlayers(new RemoveStunnedEntityPayload(event.getEntity().getId()));
            }
        }
        @SubscribeEvent
        public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event){
            PotionBrewing.Builder builder = event.getBuilder();

            builder.addMix(
                    Potions.AWKWARD,
                    Items.TNT,
                    NuancedCombatPotions.COMBUSTION
            );
        }
    }
}
