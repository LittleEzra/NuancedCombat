package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.ExplosiveArrow;
import com.feliscape.nuanced_combat.content.entity.projectile.PrismarineArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PrismarineArrowRenderer extends ArrowRenderer<PrismarineArrow> {
    private static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/prismarine_arrow.png");

    public PrismarineArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(PrismarineArrow pEntity) {
        return TEXTURE;
    }
}
