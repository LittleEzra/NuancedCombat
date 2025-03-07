package com.feliscape.nuanced_combat.data.datagen.language;

import com.feliscape.nuanced_combat.NuancedCombat;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.function.Supplier;

public abstract class NCLanguageProvider extends LanguageProvider {
    public NCLanguageProvider(PackOutput output, String locale) {
        super(output, NuancedCombat.MOD_ID, locale);
    }

    protected void addBlockAndItem(Supplier<? extends Block> key, String name) {
        this.addBlock(key, name);
        this.addItem(key.get()::asItem, name);
    }

    protected void addItemTooltip(Supplier<? extends Item> key, String name) {
        add(key.get().getDescriptionId() + ".tooltip", name);
    }
    protected void addMobEffect(Supplier<? extends MobEffect> key, String name) {
        add(key.get().getDescriptionId(), name);
    }
    protected void addMobEffect(Holder<? extends MobEffect> key, String name) {
        add(key.value().getDescriptionId(), name);
    }
    protected void addSubtitle(Supplier<SoundEvent> key, String name) {
        add("subtitle.{}.{}".formatted(NuancedCombat.MOD_ID, key.get().getLocation().getPath()), name);
    }
    protected void addAdvancement(String id, String title, String description) {
        add("advancements.%s.%s.title".formatted(NuancedCombat.MOD_ID, id), title);
        add("advancements.%s.%s.description".formatted(NuancedCombat.MOD_ID, id), description);
    }
    protected void addEnchantment(ResourceKey<Enchantment> key, String name) {
        add("enchantment.%s.%s".formatted(NuancedCombat.MOD_ID, key.location().getPath()), name);
    }
}
