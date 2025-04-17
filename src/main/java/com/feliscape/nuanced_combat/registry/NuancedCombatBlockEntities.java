package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.block.entity.HeavyLockerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NuancedCombatBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NuancedCombat.MOD_ID);

    public static final Supplier<BlockEntityType<HeavyLockerBlockEntity>> HEAVY_LOCKER = BLOCK_ENTITIES.register("heavy_locker",
            () -> BlockEntityType.Builder.of(HeavyLockerBlockEntity::new, NuancedCombatBlocks.HEAVY_LOCKER.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
