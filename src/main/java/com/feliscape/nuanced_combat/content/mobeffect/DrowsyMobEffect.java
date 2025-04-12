package com.feliscape.nuanced_combat.content.mobeffect;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DrowsyMobEffect extends MobEffect {

    public DrowsyMobEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, NuancedCombat.location("effect.drowsy.movement_speed"), -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, NuancedCombat.location("effect.drowsy.attack_speed"), -0.35D, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }
}