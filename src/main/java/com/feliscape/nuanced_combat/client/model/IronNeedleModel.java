package com.feliscape.nuanced_combat.client.model;// Made with Blockbench 4.12.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.content.entity.projectile.ThrownIronNeedle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class IronNeedleModel extends Model {
	public static final ResourceLocation TEXTURE = NuancedCombat.location("textures/entity/projectile/iron_needle.png");
	private final ModelPart root;

	public IronNeedleModel(ModelPart root) {
		super(RenderType::entitySolid);
		this.root = root;
	}

	public static LayerDefinition createLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main",
				CubeListBuilder.create().texOffs(0, -16)
						.addBox(0.0F, -2.5F, -8.0F, 0.0F, 5.0F, 16.0F,
				new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube = main.addOrReplaceChild("cube",
				CubeListBuilder.create().texOffs(0, -16)
						.addBox(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 16.0F,
				new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		this.root.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}