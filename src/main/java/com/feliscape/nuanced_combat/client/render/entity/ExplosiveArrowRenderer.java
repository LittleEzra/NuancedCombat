package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrow> {
    private static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/explosive_arrow.png");

    public ExplosiveArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveArrow pEntity) {
        return TEXTURE;
    }
}
