package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.WingedArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WingedArrowRenderer extends ArrowRenderer<WingedArrow> {
    private static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/winged_arrow.png");

    public WingedArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(WingedArrow pEntity) {
        return TEXTURE;
    }
}
