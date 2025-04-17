package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.component.PotionBundleContents;
import com.feliscape.nuanced_combat.content.item.*;
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
            p -> new ExplosiveArrowItem(p.component(NuancedCombatComponents.POWER, 3)));
    public static final DeferredItem<WingedArrowItem> WINGED_ARROW = ITEMS.registerItem("winged_arrow", WingedArrowItem::new);
    public static final DeferredItem<PrismarineArrowItem> PRISMARINE_ARROW = ITEMS.registerItem("prismarine_arrow", PrismarineArrowItem::new);

    public static final DeferredItem<WavehammerItem> WAVEHAMMER = ITEMS.registerItem("wavehammer",
            p -> new WavehammerItem(p.attributes(WavehammerItem.createAttributes()).stacksTo(1).durability(500)));
    public static final DeferredItem<ImplosionDeviceItem> IMPLOSION_DEVICE = ITEMS.registerItem("implosion_device",
            p -> new ImplosionDeviceItem(p.stacksTo(1)));
    public static final DeferredItem<BoomerangItem> BOOMERANG = ITEMS.registerItem("boomerang",
            p -> new BoomerangItem(p.stacksTo(1).durability(250)));

    public static final DeferredItem<PotionBundleItem> POTION_BUNDLE = ITEMS.registerItem("potion_bundle",
            p -> new PotionBundleItem(p
                    .component(NuancedCombatComponents.POTION_BUNDLE_USES,20)
                    .component(NuancedCombatComponents.MAX_POTION_BUNDLE_USES,20)
                    .component(NuancedCombatComponents.POTION_BUNDLE_CONTENTS, PotionBundleContents.DEFAULT)
                    .stacksTo(1)
            ));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
