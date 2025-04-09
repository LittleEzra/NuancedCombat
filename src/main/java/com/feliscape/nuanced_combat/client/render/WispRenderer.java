package com.feliscape.nuanced_combat.client.render;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.NuancedCombatModelLayers;
import com.feliscape.nuanced_combat.client.model.WispModel;
import com.feliscape.nuanced_combat.client.rendertype.NuancedCombatRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class WispRenderer implements ResourceManagerReloadListener {
    public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            NuancedCombat.location("textures/entity/wisp_0.png"),
            NuancedCombat.location("textures/entity/wisp_1.png"),
            NuancedCombat.location("textures/entity/wisp_2.png")
    };

    private Model model;

    private static void translatePoseStackInWorldSpace(PoseStack poseStack, Vec3 position){
        var cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(position.x - cameraPosition.x, position.y - cameraPosition.y, position.z - cameraPosition.z);
    }
    private static void translatePoseStackInWorldSpace(PoseStack poseStack, double x, double y, double z){
        var cameraPosition = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(x - cameraPosition.x, y - cameraPosition.y, z - cameraPosition.z);
    }

    public WispRenderer(EntityModelSet modelSet){

    }

    public void render(Entity entity, PoseStack poseStack, MultiBufferSource bufferSource, double distance, float partialTick, int packedLight, int packedOverlay){
        if (distance > 16.0D) return;
        if (model == null) return;
        poseStack.pushPose();
        poseStack.setIdentity();
        Vec3 position = entity.getPosition(partialTick);
        translatePoseStackInWorldSpace(poseStack, position.x, position.y + entity.getBbHeight() * 0.5D, position.z);

        float scale = falloff((float) distance / 16.0F, 3.0F);
        poseStack.scale(scale, scale, scale);
        poseStack.scale(-1F, -1F, -1F);

        var texture = TEXTURES[Mth.floor((entity.tickCount + partialTick) / 8.0F) % TEXTURES.length];
        model.renderToBuffer(poseStack, bufferSource.getBuffer(NuancedCombatRenderTypes.wisp(texture)), packedLight, packedOverlay);

        poseStack.popPose();
    }

    private static float falloff(float x, float power) {
        return -(float) Math.pow(x, power) + 1.0F;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        model = new WispModel(Minecraft.getInstance().getEntityModels().bakeLayer(NuancedCombatModelLayers.WISP));
    }
}
