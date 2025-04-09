package com.feliscape.nuanced_combat.registry;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.attachments.SoaringData;
import com.feliscape.nuanced_combat.content.attachments.SteelNeedleData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class NuancedCombatDataAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NuancedCombat.MOD_ID);

    public static final Supplier<AttachmentType<SoaringData>> SOARING = ATTACHMENT_TYPE.register(
            "soaring", () -> AttachmentType.builder(SoaringData::new).build()
    );
    public static final Supplier<AttachmentType<SteelNeedleData>> STEEL_NEEDLES = ATTACHMENT_TYPE.register(
            "steel_needles", () -> AttachmentType.serializable(SteelNeedleData::new).build()
    );

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPE.register(eventBus);
    }
}
