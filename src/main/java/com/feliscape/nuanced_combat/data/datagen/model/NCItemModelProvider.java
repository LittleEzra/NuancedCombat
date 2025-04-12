package com.feliscape.nuanced_combat.data.datagen.model;


import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class NCItemModelProvider extends ItemModelProvider {

    public NCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NuancedCombat.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(NuancedCombatItems.EXPLOSIVE_ARROW.get());
        handheldItem(NuancedCombatItems.WAVEHAMMER.get());
        potionBundleItem(NuancedCombatItems.POTION_BUNDLE.get());


        // discarded ideas
        basicItem(NuancedCombatItems.IRON_NEEDLE.get());
        basicItem(NuancedCombatItems.STEEL_NEEDLE.get());
    }

    public ItemModelBuilder potionBundleItem(Item item) {
        return this.potionBundleItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public ItemModelBuilder potionBundleItem(ResourceLocation item) {
        return this.getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("nuanced_combat:item/potion_bundle_template"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath() + "_layer1"))
                .texture("layer2", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath() + "_layer2"))
                .texture("layer3", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath() + "_layer3"))
                ;
    }
}
