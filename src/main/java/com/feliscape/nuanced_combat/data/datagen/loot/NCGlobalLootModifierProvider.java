package com.feliscape.nuanced_combat.data.datagen.loot;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import com.feliscape.nuanced_combat.data.loot.modifiers.AddEnchantmentModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class NCGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public NCGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, NuancedCombat.MOD_ID);
    }

    @Override
    protected void start() {
        add("add_stunning_enchantment_to_trial_vault",
                new AddEnchantmentModifier(new LootItemCondition[]{
                    LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("chests/trial_chambers/reward_common")).build(),
                    LootItemRandomChanceCondition.randomChance(0.2F).build()
                },
                    registries.holderOrThrow(NuancedCombatEnchantments.STUNNING), 1, 2),
                List.of()
                );
    }
}
