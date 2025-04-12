package com.feliscape.nuanced_combat.data.datagen;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.datagen.advancement.NCAdvancements;
import com.feliscape.nuanced_combat.data.datagen.language.NCEnUsProvider;
import com.feliscape.nuanced_combat.data.datagen.loot.NCBlockLootTableProvider;
import com.feliscape.nuanced_combat.data.datagen.loot.NCGlobalLootModifierProvider;
import com.feliscape.nuanced_combat.data.datagen.model.NCBlockModelProvider;
import com.feliscape.nuanced_combat.data.datagen.model.NCItemModelProvider;
import com.feliscape.nuanced_combat.data.datagen.recipe.NCRecipeProvider;
import com.feliscape.nuanced_combat.data.datagen.tag.NCBlockTagGenerator;
import com.feliscape.nuanced_combat.data.datagen.tag.NCDamageTypeTagGenerator;
import com.feliscape.nuanced_combat.data.datagen.tag.NCEnchantmentTagGenerator;
import com.feliscape.nuanced_combat.data.datagen.tag.NCItemTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NuancedCombat.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();



        NCGeneratedEntries generatedEntries = new NCGeneratedEntries(packOutput, lookupProvider);
        lookupProvider = generatedEntries.getRegistryProvider();
        generator.addProvider(true, generatedEntries);

        generator.addProvider(true, new NCRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(true, new NCAdvancements(packOutput, existingFileHelper, lookupProvider));

        var blockTags = new NCBlockTagGenerator(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTags);
        generator.addProvider(true, new NCItemTagGenerator(packOutput, lookupProvider, blockTags.contentsGetter()));
        generator.addProvider(true, new NCDamageTypeTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(true, new NCEnchantmentTagGenerator(packOutput, lookupProvider));

        //generator.addProvider(true, new NCGlobalLootModifierProvider(packOutput, lookupProvider));

        //generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
        //        List.of(new LootTableProvider.SubProviderEntry(NCBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        generator.addProvider(true, new NCBlockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new NCItemModelProvider(packOutput, existingFileHelper));

        generator.addProvider(true, new NCEnUsProvider(packOutput));
    }
}
