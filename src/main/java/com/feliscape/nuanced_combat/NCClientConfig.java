package com.feliscape.nuanced_combat;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NCClientConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue STUN_EFFECT_TRAIL_RESOLUTION;

    static {
        BUILDER.push("Nuanced Combat Client Config");
        BUILDER.comment("Used to modify graphical settings");
        BUILDER.push("Effects");
        STUN_EFFECT_TRAIL_RESOLUTION = BUILDER
                .comment("The resolution of the trails of the stun effect. Turns the trails off at 0.")
                .defineInRange("stunEffectTrailResolution", 4, 0, 32);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
