package com.feliscape.nuanced_combat.data.damagetypes;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;

public class NuancedCombatDamageTypes {
    public static final ResourceKey<DamageType> IRON_NEEDLE = ResourceKey.create(Registries.DAMAGE_TYPE,
            NuancedCombat.location("iron_needle"));
    public static final ResourceKey<DamageType> BLEEDING = ResourceKey.create(Registries.DAMAGE_TYPE,
            NuancedCombat.location("bleeding"));

    public static void bootstrap(BootstrapContext<DamageType> context){
        context.register(IRON_NEEDLE, new DamageType(IRON_NEEDLE.location().toString(),
                0.1f,
                DamageEffects.HURT)
        );
        context.register(BLEEDING, new DamageType(BLEEDING.location().toString(),
                0.2f,
                DamageEffects.HURT)
        );
    }
}
