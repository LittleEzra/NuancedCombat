package com.feliscape.nuanced_combat.client.model;// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class WispModel extends Model {
	private final ModelPart root;

	public WispModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.root = root;
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F,
							new CubeDeformation(0.0F)),
							PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition flame_1 = main.addOrReplaceChild("flame_1",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(0.0F, -7.0F, -4.0F, 0.0F, 10.0F, 8.0F,
								new CubeDeformation(0.0F)),
								PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition flame_2 = main.addOrReplaceChild("flame_2",
				CubeListBuilder.create()
						.texOffs(0, 0)
						.addBox(0.0F, -7.0F, -4.0F, 0.0F, 10.0F, 8.0F,
								new CubeDeformation(0.0F)),
								PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}