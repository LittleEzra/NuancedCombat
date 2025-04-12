package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NuancedCombatSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, NuancedCombat.MOD_ID);

    public static final Supplier<SoundEvent> ITEM_CHARGED = registerSoundEvent("item.charged");

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name)
    {
        return registerSoundEvent(name, 4f);
    }
    private static Supplier<SoundEvent> registerSoundEvent(String name, float pRange)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(NuancedCombat.location(name), pRange));
    }
}
