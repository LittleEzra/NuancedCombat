package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.NuancedCombatModelLayers;
import com.feliscape.nuanced_combat.client.model.ImplosionDeviceModel;
import com.feliscape.nuanced_combat.content.entity.ImplosionDevice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ImplosionDeviceRenderer extends EntityRenderer<ImplosionDevice> {
    public static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/implosion_device.png");

    private final ImplosionDeviceModel model;

    public ImplosionDeviceRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ImplosionDeviceModel(context.bakeLayer(NuancedCombatModelLayers.IMPLOSION_DEVICE));
    }

    @Override
    public void render(ImplosionDevice implosionDevice, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(implosionDevice, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityYaw));
        poseStack.scale(-1F, -1F, 1F);

        int overlay = OverlayTexture.NO_OVERLAY;

        if (implosionDevice.getCharge() >= 0 && implosionDevice.getCharge() < 300) {
            float charge = (float) implosionDevice.getCharge() + partialTick;
            float progress = charge / 200F;
            float offsetProgress = Math.max(charge - 100.0F, 0.0F) / 200F;
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.wrapDegrees((progress * progress) * 360.0F * 20.0F)));
            overlay = OverlayTexture.pack(OverlayTexture.u(offsetProgress), OverlayTexture.v(false));
        }

        VertexConsumer buffer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(poseStack, buffer, packedLight, overlay);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(ImplosionDevice implosionDevice) {
        return TEXTURE;
    }
}
