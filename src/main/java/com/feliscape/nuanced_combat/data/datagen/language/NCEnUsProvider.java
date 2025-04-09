package com.feliscape.nuanced_combat.data.datagen.language;

import com.feliscape.nuanced_combat.data.damagetypes.NuancedCombatDamageTypes;
import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;

public class NCEnUsProvider extends NCLanguageProvider {
    public NCEnUsProvider(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addItem(NuancedCombatItems.EXPLOSIVE_ARROW, "Explosive Arrow");
        this.addItem(NuancedCombatItems.IRON_NEEDLE, "Iron Needle");
        this.addItem(NuancedCombatItems.STEEL_NEEDLE, "Steel Needle");

        this.addEntityType(NuancedCombatEntityTypes.EXPLOSIVE_ARROW, "Explosive Arrow");
        this.addEntityType(NuancedCombatEntityTypes.IRON_NEEDLE, "Iron Needle");
        this.addEntityType(NuancedCombatEntityTypes.STEEL_NEEDLE, "Steel Needle");

        this.addMobEffect((Holder<? extends MobEffect>) NuancedCombatMobEffects.STUN, "Stun");
        this.addMobEffect((Holder<? extends MobEffect>) NuancedCombatMobEffects.THROUGHSIGHT, "Throughsight");
        this.addEnchantment(NuancedCombatEnchantments.STUNNING, "Stunning");

        this.add("item.nuanced_combat.explosive_arrow.power", "Power: %1$s");

        this.addDeathMessage(NuancedCombatDamageTypes.IRON_NEEDLE, "%1$s was impaled by %2$s");
        this.addDeathMessageItem(NuancedCombatDamageTypes.IRON_NEEDLE, "%1$s was impaled by %2$s with %3$s");
        this.addDeathMessage(NuancedCombatDamageTypes.BLEEDING, "%1$s bled out");
        this.addDeathMessagePlayer(NuancedCombatDamageTypes.BLEEDING, "%1$s bled out whilst trying to escape %2$s");
    }
}
