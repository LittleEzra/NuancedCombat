package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.mobeffect.NoTickMobEffect;
import com.feliscape.nuanced_combat.content.mobeffect.StunMobEffect;
import com.feliscape.nuanced_combat.util.ColorUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuancedCombatMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, NuancedCombat.MOD_ID);

    public static final DeferredHolder<MobEffect, StunMobEffect> STUN = MOB_EFFECTS.register(
            "stun", () -> new StunMobEffect(MobEffectCategory.HARMFUL, ColorUtil.getIntColor("#fad64a"))
    );

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
