package com.feliscape.nuanced_combat.client.rendertype;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class NuancedCombatRenderTypes {
    public static final BiFunction<ResourceLocation, Boolean, RenderType> WISP =
            Util.memoize((texture, createComposite) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setCullState(NO_CULL)
                .setDepthTestState(NO_DEPTH_TEST)
                .setWriteMaskState(COLOR_WRITE).
                setOverlayState(OVERLAY)
                .createCompositeState(createComposite);
        return RenderType.create("ephemeral_shine", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
    });
    public static final BiFunction<ResourceLocation, Boolean, RenderType> THROUGHSIGHT_STAR =
            Util.memoize((texture, createComposite) -> {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setDepthTestState(NO_DEPTH_TEST)
                .setWriteMaskState(COLOR_WRITE)
                .setOverlayState(OVERLAY)
                .createCompositeState(createComposite);
        return RenderType.create("ephemeral_shine", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
    });

    public static RenderType wisp(ResourceLocation location, boolean outline) {
        return WISP.apply(location, outline);
    }
    public static RenderType wisp(ResourceLocation location) {
        return WISP.apply(location, false);
    }
    public static RenderType throughsightStar(ResourceLocation location, boolean outline) {
        return WISP.apply(location, outline);
    }
    public static RenderType throughsightStar(ResourceLocation location) {
        return WISP.apply(location, false);
    }
}
