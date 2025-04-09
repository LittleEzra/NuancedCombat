package com.feliscape.nuanced_combat.content.mixin;

import com.feliscape.nuanced_combat.networking.packets.RemoveStunnedEntityPayload;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
    @Shadow
    public boolean hasEffect(Holder<MobEffect> effect) {
        throw new AbstractMethodError("Shadow called directly");
    }

    @Inject(method = "remove", at = @At("HEAD"))
    public void onRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        if (hasEffect(NuancedCombatMobEffects.STUN)){
            PacketDistributor.sendToAllPlayers(new RemoveStunnedEntityPayload(getId()));
        }
    }

    @Inject(method = "canAttack", at = @At("HEAD"), cancellable = true)
    public void onCanAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (hasEffect(NuancedCombatMobEffects.STUN)) cir.setReturnValue(false);
    }

    /*@Inject(method = "shouldDiscardFriction", at = @At("HEAD"), cancellable = true)
    public void removeFriction(CallbackInfoReturnable<Boolean> cir) {
        if (this.getType() == EntityType.PLAYER && !this.onGround()) cir.setReturnValue(true);
    }*/
}
