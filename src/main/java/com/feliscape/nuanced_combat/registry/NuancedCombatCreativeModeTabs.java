package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.content.item.ExplosiveArrowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class NuancedCombatCreativeModeTabs {

    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT){
            //createAllExplosiveArrows(event);
            event.accept(ExplosiveArrowItem.forStrength(3));
            event.accept(NuancedCombatItems.WINGED_ARROW);
            event.accept(NuancedCombatItems.PRISMARINE_ARROW);
            event.accept(NuancedCombatItems.BOOMERANG);
            event.accept(NuancedCombatItems.WAVEHAMMER);
            event.accept(NuancedCombatItems.IMPLOSION_DEVICE);
            event.accept(NuancedCombatItems.POTION_BUNDLE);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(NuancedCombatBlocks.HEAVY_LOCKER);
        }
    }


    private static void createAllExplosiveArrows(CreativeModeTab.Output pOutput){
        for(int i = 1; i <= 3; i++) {
            ItemStack itemstack = new ItemStack(NuancedCombatItems.EXPLOSIVE_ARROW.get());
            ExplosiveArrowItem.setStrength(itemstack, i);
            pOutput.accept(itemstack, i == 3 ? CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS : CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY);
        }
    }
}
