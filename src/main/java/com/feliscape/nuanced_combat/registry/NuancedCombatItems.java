package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import com.feliscape.nuanced_combat.content.item.IronNeedleItem;
import com.feliscape.nuanced_combat.content.item.SteelNeedleItem;
import net.minecraft.world.item.EmptyMapItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NuancedCombatItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(NuancedCombat.MOD_ID);

    /*public static final DeferredItem<EmptyMapItem> WAYFINDER_MAP = ITEMS.registerItem("wayfinder_map",
            EmptyMapItem::new);*/
    public static final DeferredItem<ExplosiveArrowItem> EXPLOSIVE_ARROW = ITEMS.registerItem("explosive_arrow",
            p -> new ExplosiveArrowItem(p.component(NuancedCombatComponents.POWER, 1)));
    public static final DeferredItem<IronNeedleItem> IRON_NEEDLE = ITEMS.registerItem("iron_needle",
            IronNeedleItem::new);
    public static final DeferredItem<SteelNeedleItem> STEEL_NEEDLE = ITEMS.registerItem("steel_needle",
            SteelNeedleItem::new);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
