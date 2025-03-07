package com.feliscape.nuanced_combat.content.event;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class Events {
    @EventBusSubscriber(modid = NuancedCombat.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    static class GameEvents {
        @SubscribeEvent
        public static void onLivingIncomingDamage(LivingIncomingDamageEvent event){
            if (event.getSource().getEntity() instanceof LivingEntity living && living.hasEffect(NuancedCombatMobEffects.STUN)){
                event.setCanceled(true);
            }
        }
        @SubscribeEvent
        public static void afterEntityTick(EntityTickEvent.Post event){
            if (event.getEntity() instanceof Mob mob && mob.hasEffect(NuancedCombatMobEffects.STUN)){
                mob.getNavigation().stop();
            }
        }
        @SubscribeEvent
        public static void onLivingIncomingDamage(LivingShieldBlockEvent event){
            if (event.getBlocked() && event.getDamageSource().getDirectEntity() instanceof LivingEntity livingAttacker &&
                    event.getEntity().getUseItem().canPerformAction(ItemAbilities.SHIELD_BLOCK)){
                var lookup = event.getEntity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                int level = event.getEntity().getUseItem().getEnchantmentLevel(lookup.getOrThrow(NuancedCombatEnchantments.STUNNING));

                int duration = 20 * (level);
                if (livingAttacker instanceof Player) duration /= 2;

                livingAttacker.addEffect(new MobEffectInstance(NuancedCombatMobEffects.STUN, duration));
                event.setCanceled(true);
            }
        }
    }
}
