package com.feliscape.nuanced_combat.data.datagen.model;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NCBlockModelProvider extends BlockStateProvider {
    public NCBlockModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NuancedCombat.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
