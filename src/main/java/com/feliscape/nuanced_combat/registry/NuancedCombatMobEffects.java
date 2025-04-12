package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.mobeffect.CombustionMobEffect;
import com.feliscape.nuanced_combat.content.mobeffect.DrowsyMobEffect;
import com.feliscape.nuanced_combat.content.mobeffect.NoTickMobEffect;
import com.feliscape.nuanced_combat.content.mobeffect.StunMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuancedCombatMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, NuancedCombat.MOD_ID);

    public static final DeferredHolder<MobEffect, StunMobEffect> STUN = MOB_EFFECTS.register(
            "stun", () -> new StunMobEffect(MobEffectCategory.HARMFUL, 0xfad64a)
    );
    public static final DeferredHolder<MobEffect, NoTickMobEffect> THROUGHSIGHT = MOB_EFFECTS.register(
            "throughsight", () -> new NoTickMobEffect(MobEffectCategory.BENEFICIAL, 0xedd1e7)
    );
    public static final DeferredHolder<MobEffect, CombustionMobEffect> COMBUSTION = MOB_EFFECTS.register(
            "combustion", () -> new CombustionMobEffect(MobEffectCategory.HARMFUL, 0xc12604)
    );
    public static final DeferredHolder<MobEffect, DrowsyMobEffect> DROWSY = MOB_EFFECTS.register(
            "drowsy", () -> new DrowsyMobEffect(MobEffectCategory.HARMFUL, 0x8b9bda)
    );

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
