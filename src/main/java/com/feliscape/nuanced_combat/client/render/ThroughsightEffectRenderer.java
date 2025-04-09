package com.feliscape.nuanced_combat.client.render;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.rendertype.NuancedCombatRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ThroughsightEffectRenderer {
    private static final ResourceLocation BASE_TEXTURE = NuancedCombat.location("textures/effect/shine.png");
    private static final ResourceLocation STAR_0_TEXTURE = NuancedCombat.location("textures/effect/shine_star0.png");
    private static final ResourceLocation STAR_1_TEXTURE = NuancedCombat.location("textures/effect/shine_star1.png");

    private static void translatePoseStackInWorldSpace(PoseStack poseStack, Vec3 position){
        var cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(position.x - cameraPosition.x, position.y - cameraPosition.y, position.z - cameraPosition.z);
    }

    public static void renderEffectAtPosition(PoseStack poseStack,
                                              MultiBufferSource bufferSource,
                                              Entity entity,
                                              float partialTick,
                                              int packedLight,
                                              EntityRenderDispatcher entityRenderDispatcher,
                                              double distance){
        if (distance > 16.0D) return;

        Vec3 position = entity.getPosition(partialTick).add(0D, entity.getBbHeight() * 0.5D, 0D);

        poseStack.pushPose();
        poseStack.setIdentity();
        translatePoseStackInWorldSpace(poseStack, position); // Translate to entity position
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation()); // Align to camera
        float scale = falloff((float) distance / 16.0F, 3F) * 2.0F;
        scale *= (Mth.sin((entity.tickCount + partialTick) * 0.05F) * 0.2F) + 1F;
        poseStack.scale(scale, scale, scale);

        VertexConsumer buffer = bufferSource.getBuffer(NuancedCombatRenderTypes.throughsightStar(BASE_TEXTURE));
        PoseStack.Pose pose = poseStack.last();

        vertex(buffer, pose, -0.5F, -0.5F,    0.0F, 1.0F, packedLight, 128);   // Bottom Left
        vertex(buffer, pose, 0.5F, -0.5F,     1.0F, 1.0F, packedLight, 128);   // Bottom Right
        vertex(buffer, pose, 0.5F, 0.5F,      1.0F, 0.0F, packedLight, 128);   // Top Right
        vertex(buffer, pose, -0.5F, 0.5F,     0.0F, 0.0F, packedLight, 128);   // Top Left

        poseStack.popPose();

        //renderStar(poseStack, bufferSource.getBuffer(NuancedCombatRenderTypes.entityTranslucentEmissiveNoDepth(STAR_0_TEXTURE)),
        //        position, distance, entity.tickCount + partialTick, partialTick, packedLight, 0.1F, entityRenderDispatcher);
        //renderStar(poseStack, bufferSource.getBuffer(NuancedCombatRenderTypes.entityTranslucentEmissiveNoDepth(STAR_1_TEXTURE)),
        //        position, distance, entity.tickCount + partialTick, partialTick, packedLight, -0.1F, entityRenderDispatcher);
    }

    private static float falloff(float x, float power) {
        return -(float) Math.pow(x, power) + 1.0F;
    }

    private static void renderStar(PoseStack poseStack,
                                   VertexConsumer buffer,
                                   Vec3 position,
                                   double distance,
                                   float tickCount,
                                   float partialTick,
                                   int packedLight,
                                   float rotationSpeed,
                                   EntityRenderDispatcher entityRenderDispatcher) {

        poseStack.pushPose();
        poseStack.setIdentity();
        translatePoseStackInWorldSpace(poseStack, position); // Translate to entity position
        poseStack.mulPose(entityRenderDispatcher.cameraOrientation()); // Align to camera
        poseStack.mulPose(Axis.ZP.rotation(tickCount * rotationSpeed));
        //poseStack.rotateAround(entityRenderDispatcher.cameraOrientation(), 0F, 0F, tickCount * rotationSpeed);
        float scale = falloff((float) distance / 16.0F, 2F);
        poseStack.scale(scale, scale, scale);

        PoseStack.Pose pose = poseStack.last();

        vertex(buffer, pose, -0.5F, -0.5F,    0.0F, 1.0F, packedLight, 255);   // Bottom Left
        vertex(buffer, pose, 0.5F, -0.5F,     1.0F, 1.0F, packedLight, 255);   // Bottom Right
        vertex(buffer, pose, 0.5F, 0.5F,      1.0F, 0.0F, packedLight, 255);   // Top Right
        vertex(buffer, pose, -0.5F, 0.5F,     0.0F, 0.0F, packedLight, 255);   // Top Left
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, float x, float y, float u, float v, int packedLight, int alpha) {
        consumer.addVertex(pose, x, y, 0.0F)
                .setColor(255, 255, 255, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}
