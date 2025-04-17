package com.feliscape.nuanced_combat.client.model;

import com.feliscape.nuanced_combat.content.entity.ImplosionDevice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ImplosionDeviceModel extends EntityModel<ImplosionDevice> {
    private final ModelPart root;

    public ImplosionDeviceModel(ModelPart root) {
        this.root = root.getChild("Root");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 10).addBox(0.0F, -7.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 10).addBox(-4.0F, -4.0F, 0.5F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Root.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                        .texOffs(12, 10).addBox(-2.0F, -3.0F, 0.0F, 2.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -5.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r2 = Root.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(0, 19).addBox(-3.0F, -7.0F, 0.0F, 6.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, 3.0F, 0.5F, 0.0F, -0.3927F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(ImplosionDevice implosionDevice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}