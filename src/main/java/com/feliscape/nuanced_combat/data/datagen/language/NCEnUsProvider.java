package com.feliscape.nuanced_combat.data.datagen.language;

import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageTypes;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import com.feliscape.nuanced_combat.registry.*;
import net.minecraft.data.PackOutput;

public class NCEnUsProvider extends NCLanguageProvider {
    public NCEnUsProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlock(NuancedCombatBlocks.HEAVY_LOCKER, "Heavy Locker");

        this.addItem(NuancedCombatItems.EXPLOSIVE_ARROW, "Explosive Arrow");
        this.addItem(NuancedCombatItems.WINGED_ARROW, "Winged Arrow");
        this.addItem(NuancedCombatItems.PRISMARINE_ARROW, "Prismarine Arrow");

        this.addItem(NuancedCombatItems.BOOMERANG, "Boomerang");

        this.addItem(NuancedCombatItems.WAVEHAMMER, "Wavehammer");
        this.addItem(NuancedCombatItems.IMPLOSION_DEVICE, "Implosion Device");

        this.addItem(NuancedCombatItems.POTION_BUNDLE, "Bundle of Potions");
        this.add("item.nuanced_combat.potion_bundle.effects_header", "Effects:");

        this.addEntityType(NuancedCombatEntityTypes.EXPLOSIVE_ARROW, "Explosive Arrow");
        this.addEntityType(NuancedCombatEntityTypes.WINGED_ARROW, "Winged Arrow");
        this.addEntityType(NuancedCombatEntityTypes.PRISMARINE_ARROW, "Prismarine Arrow");
        this.addEntityType(NuancedCombatEntityTypes.BOOMERANG, "Boomerang");
        this.addEntityType(NuancedCombatEntityTypes.IMPLOSION_DEVICE, "Implosion Device");

        this.addMobEffect(NuancedCombatMobEffects.STUN, "Stun");
        this.addMobEffect(NuancedCombatMobEffects.THROUGHSIGHT, "Throughsight");
        this.addMobEffect(NuancedCombatMobEffects.COMBUSTION, "Combustion");
        this.addMobEffect(NuancedCombatMobEffects.DROWSY, "Drowsy");

        this.addPotion(NuancedCombatPotions.COMBUSTION, "Combustion");

        this.addEnchantment(NuancedCombatEnchantments.STUNNING, "Stunning");

        this.add("item.nuanced_combat.explosive_arrow.power", "Power: %1$s");

        this.addSubtitle(NuancedCombatSoundEvents.ITEM_CHARGED, "Item finishes charging");
        this.addSubtitle(NuancedCombatSoundEvents.IMPLOSION_DEVICE_CHARGING, "Implosion Device charges");
        this.addSubtitle(NuancedCombatSoundEvents.IMPLOSION_DEVICE_DISCHARGE, "Implosion Device discharges");

        this.addDeathMessage(NuancedCombatDamageTypes.IRON_NEEDLE, "%1$s was impaled by %2$s");
        this.addDeathMessageItem(NuancedCombatDamageTypes.IRON_NEEDLE, "%1$s was impaled by %2$s with %3$s");
        this.addDeathMessage(NuancedCombatDamageTypes.BOOMERANG, "%1$s was hit by a boomerang from %2$s");
        this.addDeathMessageItem(NuancedCombatDamageTypes.BOOMERANG, "%1$s was hit by %2$s with %3$s");
        this.addDeathMessage(NuancedCombatDamageTypes.BLEEDING, "%1$s bled out");
        this.addDeathMessagePlayer(NuancedCombatDamageTypes.BLEEDING, "%1$s bled out whilst trying to escape %2$s");

        this.addAdvancement("drink_combustion_potion", "Spontaneous Combustion", "Uh, maybe that wasn't the best idea...");
    }
}
