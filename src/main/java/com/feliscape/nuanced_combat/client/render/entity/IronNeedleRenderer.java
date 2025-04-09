package com.feliscape.nuanced_combat.client.render.entity;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.NuancedCombatModelLayers;
import com.feliscape.nuanced_combat.client.model.IronNeedleModel;
import com.feliscape.nuanced_combat.content.entity.projectile.ThrownIronNeedle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;

public class IronNeedleRenderer extends EntityRenderer<ThrownIronNeedle> {
    private static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/iron_needle.png");
    private final IronNeedleModel model;

    public IronNeedleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new IronNeedleModel(context.bakeLayer(NuancedCombatModelLayers.IRON_NEEDLE));
    }


    public void render(ThrownIronNeedle entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownIronNeedle thrownIronNeedle) {
        return TEXTURE;
    }
}
