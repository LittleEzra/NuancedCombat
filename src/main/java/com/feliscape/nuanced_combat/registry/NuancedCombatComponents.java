package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.component.PotionBundleContents;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NuancedCombatComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NuancedCombat.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> POWER = DATA_COMPONENTS.registerComponentType(
            "power", b -> b.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final Supplier<DataComponentType<Integer>> POTION_BUNDLE_USES = DATA_COMPONENTS.registerComponentType(
            "potion_bundle_uses", b -> b.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final Supplier<DataComponentType<Integer>> MAX_POTION_BUNDLE_USES = DATA_COMPONENTS.registerComponentType(
            "max_potion_bundle_uses", b -> b.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final Supplier<DataComponentType<PotionBundleContents>> POTION_BUNDLE_CONTENTS = DATA_COMPONENTS.registerComponentType(
            "potion_bundle_contents", b -> b.persistent(PotionBundleContents.CODEC).networkSynchronized(PotionBundleContents.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus){
        DATA_COMPONENTS.register(eventBus);
    }
}
