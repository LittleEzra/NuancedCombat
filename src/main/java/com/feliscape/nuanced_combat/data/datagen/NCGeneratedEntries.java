package com.feliscape.nuanced_combat.data.datagen;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageTypes;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NCGeneratedEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, NuancedCombatEnchantments::bootstrap)
            //.add(Registries.CONFIGURED_FEATURE, PrismaTreeFeatures::bootstrap)
            //.add(Registries.PLACED_FEATURE, DeepwoodPlacedFeatures::bootstrap)
            //.add(ForgeRegistries.Keys.BIOME_MODIFIERS, DeepwoodBiomeModifiers::bootstrap)
            .add(Registries.DAMAGE_TYPE, NuancedCombatDamageTypes::bootstrap)
            //.add(Registries.NOISE, DeepwoodNoise::bootstrap)
            ;
    public NCGeneratedEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(NuancedCombat.MOD_ID));
    }
}
