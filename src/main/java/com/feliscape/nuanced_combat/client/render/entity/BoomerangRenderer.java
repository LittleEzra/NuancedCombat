package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.NuancedCombatModelLayers;
import com.feliscape.nuanced_combat.client.model.BoomerangModel;
import com.feliscape.nuanced_combat.client.model.ImplosionDeviceModel;
import com.feliscape.nuanced_combat.content.entity.ImplosionDevice;
import com.feliscape.nuanced_combat.content.entity.projectile.Boomerang;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BoomerangRenderer extends EntityRenderer<Boomerang> {
    public static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/boomerang.png");

    private final BoomerangModel<Boomerang> model;

    public BoomerangRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BoomerangModel(context.bakeLayer(NuancedCombatModelLayers.BOOMERANG));
    }

    @Override
    public void render(Boomerang boomerang, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(boomerang, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) Mth.lerp((double)partialTick, boomerang.getOldSpin(), boomerang.getSpin())));
        poseStack.scale(-1F, -1F, 1F);

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(Boomerang boomerang) {
        return TEXTURE;
    }
}
