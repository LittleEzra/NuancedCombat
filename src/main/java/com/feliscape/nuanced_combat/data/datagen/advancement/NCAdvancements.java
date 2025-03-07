package com.feliscape.nuanced_combat.data.datagen.advancement;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NCAdvancements extends AdvancementProvider {
    public NCAdvancements(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new NCAdventureAdvancements()));
    }
}
