package com.feliscape.nuanced_combat.content.mixin;

import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity implements EquipmentUser, Leashable, Targeting {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    /*@Inject(method = "getTarget", at = @At("HEAD"), cancellable = true)
    public void onGetTarget(CallbackInfoReturnable<LivingEntity> cir) {
        if (hasEffect(NuancedCombatMobEffects.STUN)){
            cir.setReturnValue(null);
        }
    }
    @Inject(method = "getTargetFromBrain", at = @At("HEAD"), cancellable = true)
    protected void onGetTargetFromBrain(CallbackInfoReturnable<LivingEntity> cir) {
        if (hasEffect(NuancedCombatMobEffects.STUN)){
            cir.setReturnValue(null);
        }
    }*/
}
