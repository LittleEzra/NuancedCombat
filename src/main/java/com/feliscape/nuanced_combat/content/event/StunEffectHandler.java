package com.feliscape.nuanced_combat.content.event;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EffectParticleModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.UUID;

// Copied from the Create Mod

@EventBusSubscriber(modid = NuancedCombat.MOD_ID)
public class StunEffectHandler {
    @SubscribeEvent
    public static void hidePlayer(LivingEvent.LivingVisibilityEvent event){
        if (event.getLookingEntity() instanceof LivingEntity living && living.hasEffect(NuancedCombatMobEffects.STUN)){
            event.modifyVisibility(0);
        }
    }

    @SubscribeEvent
    public static void clearTargets(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;

        /*if (entity.tickCount % 5 != 0)
            return;*/
        if (!(entity instanceof Mob mob))
            return;

        if (entity.hasEffect(NuancedCombatMobEffects.STUN)) {
            mob.setTarget(null);
            if (mob.targetSelector != null) {
                for (WrappedGoal goal : mob.targetSelector.getAvailableGoals()) {
                    if (goal.isRunning() && goal.getGoal() instanceof TargetGoal tg)
                        tg.stop();
                }
            }

            if (entity instanceof NeutralMob neutralMob) {
                neutralMob.stopBeingAngry();
            }

            mob.setLastHurtByMob(null);
            mob.setLastHurtByPlayer(null);
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof LivingEntity living && living.hasEffect(NuancedCombatMobEffects.STUN)){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void afterEntityTick(EntityTickEvent.Post event){
        if (event.getEntity() instanceof LivingEntity living && living.hasEffect(NuancedCombatMobEffects.STUN)){
                /*if (living.level() instanceof ServerLevel serverLevel && living.tickCount % 10 == 0){
                    serverLevel.sendParticles(NuancedCombatParticles.STUN.get(), living.getX(), living.getY() + living.getBbHeight() + 0.2F, living.getZ(),
                            1, 0.0D, 0.0D, 0.0D, 0.0D);
                }*/
            if (event.getEntity() instanceof Mob mob){
                mob.getNavigation().stop();
            }
        }
    }
    @SubscribeEvent
    public static void modifyEffectParticle(EffectParticleModificationEvent event){
        if (event.getEffect().is(NuancedCombatMobEffects.STUN)){
            event.setVisible(false);
        }
    }
}
