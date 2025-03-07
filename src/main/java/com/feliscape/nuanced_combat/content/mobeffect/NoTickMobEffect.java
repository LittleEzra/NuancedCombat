package com.feliscape.nuanced_combat.content.mobeffect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NoTickMobEffect extends MobEffect {
    public NoTickMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public NoTickMobEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }
}
