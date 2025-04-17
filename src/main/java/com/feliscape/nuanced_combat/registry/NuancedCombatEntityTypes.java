package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.ImplosionDevice;
import com.feliscape.nuanced_combat.content.entity.projectile.Boomerang;
import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.PrismarineArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.WingedArrow;
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
    public static final DeferredHolder<EntityType<?>, EntityType<WingedArrow>> WINGED_ARROW = ENTITY_TYPES.register("winged_arrow",
            () -> EntityType.Builder.<WingedArrow>of(WingedArrow::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("winged_arrow").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<PrismarineArrow>> PRISMARINE_ARROW = ENTITY_TYPES.register("prismarine_arrow",
            () -> EntityType.Builder.<PrismarineArrow>of(PrismarineArrow::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("prismarine_arrow").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<ImplosionDevice>> IMPLOSION_DEVICE = ENTITY_TYPES.register("implosion_device",
            () -> EntityType.Builder.<ImplosionDevice>of(ImplosionDevice::new, MobCategory.MISC).sized(0.3125f, 0.3125f)
                    .build(NuancedCombat.location("implosion_device").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<Boomerang>> BOOMERANG = ENTITY_TYPES.register("boomerang",
            () -> EntityType.Builder.<Boomerang>of(Boomerang::new, MobCategory.MISC).sized(0.5f, 0.5f)
                    .build(NuancedCombat.location("boomerang").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
