package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.SteelNeedle;
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

public class SteelNeedleRenderer extends EntityRenderer<SteelNeedle> {
    private static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/steel_needle.png");

    public SteelNeedleRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SteelNeedle entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));

        poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
        poseStack.scale(0.05625F, 0.05625F, 0.05625F);
        poseStack.translate(-4.0F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        PoseStack.Pose posestack$pose = poseStack.last();

        for(int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            this.vertex(posestack$pose, vertexconsumer, -4F, -1F, 0, 0.0F, 0.0F, 0, 1, 0, packedLight);
            this.vertex(posestack$pose, vertexconsumer, 4F, -1F, 0, 0.25F, 0.0F, 0, 1, 0, packedLight);
            this.vertex(posestack$pose, vertexconsumer, 4F, 1F, 0, 0.25F, 0.03125F, 0, 1, 0, packedLight);
            this.vertex(posestack$pose, vertexconsumer, -4F, 1F, 0, 0.0F, 0.03125F, 0, 1, 0, packedLight);
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    public void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v, int normalX, int normalY, int normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, (float)normalX, (float)normalZ, (float)normalY);
    }

    @Override
    public ResourceLocation getTextureLocation(SteelNeedle steelNeedle) {
        return TEXTURE;
    }
}
