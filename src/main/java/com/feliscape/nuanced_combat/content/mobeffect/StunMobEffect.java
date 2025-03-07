package com.feliscape.nuanced_combat.content.mobeffect;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StunMobEffect extends MobEffect {
    public StunMobEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, NuancedCombat.location("effect.stun"), -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}