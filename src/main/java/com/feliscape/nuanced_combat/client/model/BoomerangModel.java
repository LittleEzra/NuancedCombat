package com.feliscape.nuanced_combat.client.model;

import com.feliscape.nuanced_combat.content.entity.ImplosionDevice;
import com.feliscape.nuanced_combat.content.entity.projectile.Boomerang;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class BoomerangModel<T extends Boomerang> extends EntityModel<T> {
    private final ModelPart root;

    public BoomerangModel(ModelPart root) {
        this.root = root.getChild("Root");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();


        PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 3.1416F, 0.0F, 1.5708F));

        PartDefinition cube_r1 = Root.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(0, 3).addBox(-8.0F, -1.0F, -1.0F, 7.0F, 1.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = Root.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 9.0F, 1.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 16);
    }

    @Override
    public void setupAnim(Boomerang implosionDevice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}