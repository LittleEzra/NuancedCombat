package com.feliscape.nuanced_combat.content.mixin;

import com.feliscape.nuanced_combat.content.mobeffect.CombustionMobEffect;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import com.feliscape.nuanced_combat.registry.NuancedCombatPotions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile implements ItemSupplier {
    public ThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "applySplash", at = @At("HEAD"), cancellable = true)
    private void onApplySplash(Iterable<MobEffectInstance> effects, @Nullable Entity entity, CallbackInfo ci){
        for (var inst : effects){
            if (inst.is(NuancedCombatMobEffects.COMBUSTION)){
                this.level().explode(
                        null,
                        Explosion.getDefaultDamageSource(this.level(), null),
                        CombustionMobEffect.EXPLOSION_DAMAGE_CALCULATOR,
                        this.getX(),
                        this.getY(0.5D),
                        this.getZ(),
                        2,
                        false,
                        Level.ExplosionInteraction.NONE);
                ci.cancel();
                break;
            }
        }
    }
    @Inject(method = "makeAreaOfEffectCloud", at = @At("HEAD"), cancellable = true)
    private void onMakeAreaOfEffectCloud(PotionContents potionContents, CallbackInfo ci){
        for (var inst : potionContents.customEffects()){
            if (inst.is(NuancedCombatMobEffects.COMBUSTION)){
                this.level().explode(
                        null,
                        Explosion.getDefaultDamageSource(this.level(), null),
                        CombustionMobEffect.EXPLOSION_DAMAGE_CALCULATOR,
                        this.getX(),
                        this.getY(0.5D),
                        this.getZ(),
                        2,
                        false,
                        Level.ExplosionInteraction.NONE);
                ci.cancel();
                return;
            }
        }
        if (potionContents.potion().isPresent() && potionContents.potion().get() == NuancedCombatPotions.COMBUSTION){
            this.level().explode(
                    this.getOwner(),
                    Explosion.getDefaultDamageSource(this.level(), this.getOwner()),
                    CombustionMobEffect.EXPLOSION_DAMAGE_CALCULATOR,
                    this.getX(),
                    this.getY(0.5D),
                    this.getZ(),
                    2,
                    false,
                    Level.ExplosionInteraction.NONE);
            ci.cancel();
        }
    }
}
