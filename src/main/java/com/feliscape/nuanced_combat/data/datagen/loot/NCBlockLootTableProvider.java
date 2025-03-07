package com.feliscape.nuanced_combat.data.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class NCBlockLootTableProvider extends BlockLootSubProvider {
    public NCBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

    }

    protected void dropOtherWithoutSilkTouch(Block block, ItemLike other){
        this.add(block, b -> this.createSingleItemTableWithSilkTouch(b, other));
    }

    /*@Override
    protected Iterable<Block> getKnownBlocks() {
        return PrismaBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }*/
}
