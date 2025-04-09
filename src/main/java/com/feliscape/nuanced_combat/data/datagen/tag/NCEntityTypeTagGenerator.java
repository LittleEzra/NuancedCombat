package com.feliscape.nuanced_combat.data.datagen.tag;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.registry.NuancedCombatTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class NCEntityTypeTagGenerator extends EntityTypeTagsProvider {
    public NCEntityTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(NuancedCombatTags.EntityTypes.STUN_IMMUNE)
                .addTag(Tags.EntityTypes.BOSSES);
    }
}
