package com.feliscape.nuanced_combat.data.damagetypes;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NuancedCombatDamageSources {
    public static DamageSource ironNeedle(@Nullable Entity causingEntity, Entity directEntity){
        return new DamageSource(
                directEntity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(NuancedCombatDamageTypes.IRON_NEEDLE),
                directEntity, causingEntity
        );
    }
    public static DamageSource boomerang(@Nullable Entity causingEntity, Entity directEntity){
        return new DamageSource(
                directEntity.level().registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(NuancedCombatDamageTypes.BOOMERANG),
                directEntity, causingEntity
        );
    }
    public static DamageSource bleeding(Level level){
        return new DamageSource(
                level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(NuancedCombatDamageTypes.BLEEDING), (Entity) null
        );
    }
}
