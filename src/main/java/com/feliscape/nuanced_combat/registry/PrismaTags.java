package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PrismaTags {
    public static class Blocks{

        private static TagKey<Block> create(String name){
            return TagKey.create(Registries.BLOCK, NuancedCombat.location(name));
        }
    }
    public static class Items{

        private static TagKey<Item> create(String name){
            return TagKey.create(Registries.ITEM, NuancedCombat.location(name));
        }
    }
}
