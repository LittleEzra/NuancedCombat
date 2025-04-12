package com.feliscape.nuanced_combat.client.extensions;

import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class WavehammerClientExtension implements IClientItemExtensions {

    // Apply third-person arm pose
    @Nullable
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        if (entityLiving.isUsingItem() && entityLiving.getUsedItemHand() == hand)
            return HumanoidModel.ArmPose.THROW_SPEAR;
        else return null;
    }

    // Apply first-person transform
    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProgress, float swingProgress) {
        if (player.isUsingItem()) {

            int handedness = arm == HumanoidArm.RIGHT ? 1 : -1;

            poseStack.translate((float) handedness * 0.8F, -0.8F + equipProgress * -0.6F, -0.5F);
            poseStack.translate((float) handedness * -0.5F, 0.7F, 0.1F);
            float charge = (float) itemInHand.getUseDuration(player) - ((float) player.getUseItemRemainingTicks() - partialTick + 1.0F);
            float chargeProgress = charge / 20.0F;
            if (chargeProgress > 1.0F) {
                chargeProgress = 1.0F;
            }

        /*if (chargeProgress > 0.1F) {
            float sin = Mth.sin((charge - 0.1F) * 1.3F);
            float f17 = chargeProgress - 0.1F;
            float shake = sin * f17;
            poseStack.translate(shake * 0.0F, shake * 0.004F, shake * 0.0F);
        }*/

            poseStack.mulPose(Axis.XP.rotationDegrees((1.0F - chargeProgress) * -25.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees((float) handedness * 35.3F));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) handedness * -9.785F));

            poseStack.translate(0.0F, 0.0F, chargeProgress * 0.05F);
            poseStack.scale(0.8F, 0.8F, 0.8F + chargeProgress * 0.2F);
            poseStack.mulPose(Axis.YN.rotationDegrees((float) handedness * 45.0F));
            return true;
        }
        else if (player.getCooldowns().isOnCooldown(NuancedCombatItems.WAVEHAMMER.get())){
            float cooldownPercent = player.getCooldowns().getCooldownPercent(NuancedCombatItems.WAVEHAMMER.get(), partialTick);
            cooldownPercent = Math.min(-(2.0F * cooldownPercent) + 2.0F, 1.0F);

            int handedness = arm == HumanoidArm.RIGHT ? 1 : -1;
            poseStack.translate((float)handedness * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
            poseStack.translate(0.0F, -0.2F +  easeInOutSin(cooldownPercent) * 0.2F, -0.4F + easeInOutSin(cooldownPercent) * 0.4F);
            poseStack.mulPose(Axis.XP.rotationDegrees(-125F + easeInOutSin(cooldownPercent) * 125.0F));
            applyItemArmAttackTransform(poseStack, arm, swingProgress);
            return true;
        }
        return false;
    }

    private float easeInOutSin(float t){
        return -(Mth.cos(Mth.PI * t) - 1F) / 2.0F;
    }

    private void applyItemArmAttackTransform(PoseStack poseStack, HumanoidArm hand, float swingProgress) {
        int i = hand == HumanoidArm.RIGHT ? 1 : -1;
        float f = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees((float)i * (45.0F + f * -20.0F)));
        float f1 = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
        poseStack.mulPose(Axis.ZP.rotationDegrees((float)i * f1 * -20.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(f1 * -80.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees((float)i * -45.0F));
    }
}
