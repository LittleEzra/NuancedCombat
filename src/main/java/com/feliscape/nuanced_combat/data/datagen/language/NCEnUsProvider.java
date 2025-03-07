package com.feliscape.nuanced_combat.data.datagen.language;

import com.feliscape.nuanced_combat.data.enchantments.NuancedCombatEnchantments;
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
        this.addMobEffect((Holder<? extends MobEffect>) NuancedCombatMobEffects.STUN, "Stun");
        this.addEnchantment(NuancedCombatEnchantments.STUNNING, "Stunning");
    }
}
