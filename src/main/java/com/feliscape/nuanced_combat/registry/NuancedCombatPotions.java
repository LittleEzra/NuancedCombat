package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.mobeffect.CombustionMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuancedCombatPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, NuancedCombat.MOD_ID);

    public static final Holder<Potion> COMBUSTION = POTIONS.register("combustion", name -> new Potion(
            name.getPath(),
            new MobEffectInstance(NuancedCombatMobEffects.COMBUSTION, 1)
    ));

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }
}
