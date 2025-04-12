package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.SteelNeedle;
import com.feliscape.nuanced_combat.content.entity.projectile.ThrownIronNeedle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuancedCombatEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, NuancedCombat.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<ExplosiveArrow>> EXPLOSIVE_ARROW = ENTITY_TYPES.register("explosive_arrow",
            () -> EntityType.Builder.<ExplosiveArrow>of(ExplosiveArrow::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("explosive_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownIronNeedle>> IRON_NEEDLE = ENTITY_TYPES.register("iron_needle",
            () -> EntityType.Builder.<ThrownIronNeedle>of(ThrownIronNeedle::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("iron_needle").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<SteelNeedle>> STEEL_NEEDLE = ENTITY_TYPES.register("steel_needle",
            () -> EntityType.Builder.<SteelNeedle>of(SteelNeedle::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("steel_needle").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
