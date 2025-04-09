package com.feliscape.nuanced_combat.client.itemproperties;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;

public class NuancedCombatItemOverrides {
    public static void register(){
        /*ItemProperties.register(
                NuancedCombatItems.IRON_NEEDLE.get(),
                NuancedCombat.location("is_thrown"),
                ((itemStack, clientLevel, livingEntity, seed) -> {
                    return 0F;
                })
        );*/
    }
}
