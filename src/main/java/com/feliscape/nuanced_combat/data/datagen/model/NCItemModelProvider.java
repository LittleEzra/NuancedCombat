package com.feliscape.nuanced_combat.data.datagen.model;


import com.feliscape.nuanced_combat.NuancedCombat;
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
    }

    public ItemModelBuilder mugItem(Item item) {
        return this.mugItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public ItemModelBuilder mugItem(ResourceLocation item) {
        return this.getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("brewersdelight:item/mug_item"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }
}
