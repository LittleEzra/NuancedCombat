package com.feliscape.nuanced_combat.client;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.itemproperties.NuancedCombatItemOverrides;
import com.feliscape.nuanced_combat.client.model.IronNeedleModel;
import com.feliscape.nuanced_combat.client.model.WispModel;
import com.feliscape.nuanced_combat.client.render.ThroughsightEffectRenderer;
import com.feliscape.nuanced_combat.client.render.entity.ExplosiveArrowRenderer;
import com.feliscape.nuanced_combat.client.render.entity.IronNeedleRenderer;
import com.feliscape.nuanced_combat.client.render.entity.SteelNeedleRenderer;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

public class ClientEvents {
    @EventBusSubscriber(modid = NuancedCombat.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ClientGameEvents {
        @SubscribeEvent
        public static void beforeRenderLiving(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<?>> event)
        {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasEffect(NuancedCombatMobEffects.THROUGHSIGHT)){
                event.setCanceled(true);
                if (event.getEntity() instanceof Player player &&
                        (player.hasEffect(MobEffects.INVISIBILITY) || player.getItemBySlot(EquipmentSlot.HEAD).is(Items.CARVED_PUMPKIN))) return;

                /*NuancedCombatClient.getInstance().wispRenderer().render(
                        event.getEntity(),
                        event.getPoseStack(),
                        event.getMultiBufferSource(),
                        Minecraft.getInstance().player.position().distanceTo(event.getEntity().position()),
                        event.getPartialTick(),
                        event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY
                );*/

                Vec3 position = event.getEntity().getPosition(event.getPartialTick()).add(0D, event.getEntity().getBbHeight() * 0.5D, 0D);
                ThroughsightEffectRenderer.renderEffectAtPosition(event.getPoseStack(), event.getMultiBufferSource(),
                        event.getEntity(),
                        event.getPartialTick(),
                        LightTexture.pack(15, 15),
                        Minecraft.getInstance().getEntityRenderDispatcher(),
                        Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().distanceTo(position));
            }
        }
    }

    @EventBusSubscriber(modid = NuancedCombat.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerReloadListeners(RegisterClientReloadListenersEvent event)
        {
            new NuancedCombatClient(event);
        }
        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            event.registerLayerDefinition(NuancedCombatModelLayers.IRON_NEEDLE, IronNeedleModel::createLayer);
            event.registerLayerDefinition(NuancedCombatModelLayers.WISP, WispModel::createLayer);
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(NuancedCombatEntityTypes.EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.STEEL_NEEDLE.get(), SteelNeedleRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.IRON_NEEDLE.get(), IronNeedleRenderer::new);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                NuancedCombatItemOverrides.register();
            });
        }
    }
}
