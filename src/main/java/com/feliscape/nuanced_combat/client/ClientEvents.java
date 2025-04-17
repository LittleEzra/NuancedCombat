package com.feliscape.nuanced_combat.client;

import com.feliscape.nuanced_combat.NuancedCombat;
import com.feliscape.nuanced_combat.client.extensions.WavehammerClientExtension;
import com.feliscape.nuanced_combat.client.itemproperties.NuancedCombatItemOverrides;
import com.feliscape.nuanced_combat.client.model.BoomerangModel;
import com.feliscape.nuanced_combat.client.model.ImplosionDeviceModel;
import com.feliscape.nuanced_combat.client.model.WispModel;
import com.feliscape.nuanced_combat.client.render.ThroughsightEffectRenderer;
import com.feliscape.nuanced_combat.client.render.entity.*;
import com.feliscape.nuanced_combat.content.component.PotionBundleContents;
import com.feliscape.nuanced_combat.registry.NuancedCombatComponents;
import com.feliscape.nuanced_combat.registry.NuancedCombatEntityTypes;
import com.feliscape.nuanced_combat.registry.NuancedCombatItems;
import com.feliscape.nuanced_combat.registry.NuancedCombatMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

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

                NuancedCombatClient.getInstance().wispRenderer().render(
                        event.getEntity(),
                        event.getPoseStack(),
                        event.getMultiBufferSource(),
                        Minecraft.getInstance().player.position().distanceTo(event.getEntity().position()),
                        event.getPartialTick(),
                        event.getPackedLight(),
                        OverlayTexture.NO_OVERLAY
                );

                /*Vec3 position = event.getEntity().getPosition(event.getPartialTick()).add(0D, event.getEntity().getBbHeight() * 0.5D, 0D);
                ThroughsightEffectRenderer.renderEffectAtPosition(event.getPoseStack(), event.getMultiBufferSource(),
                        event.getEntity(),
                        event.getPartialTick(),
                        LightTexture.pack(15, 15),
                        Minecraft.getInstance().getEntityRenderDispatcher(),
                        Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().distanceTo(position));*/
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
            event.registerLayerDefinition(NuancedCombatModelLayers.WISP, WispModel::createLayer);
            event.registerLayerDefinition(NuancedCombatModelLayers.IMPLOSION_DEVICE, ImplosionDeviceModel::createLayer);
            event.registerLayerDefinition(NuancedCombatModelLayers.BOOMERANG, BoomerangModel::createLayer);
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
        {
            event.registerEntityRenderer(NuancedCombatEntityTypes.EXPLOSIVE_ARROW.get(), ExplosiveArrowRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.WINGED_ARROW.get(), WingedArrowRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.PRISMARINE_ARROW.get(), PrismarineArrowRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.IMPLOSION_DEVICE.get(), ImplosionDeviceRenderer::new);
            event.registerEntityRenderer(NuancedCombatEntityTypes.BOOMERANG.get(), BoomerangRenderer::new);
        }
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                NuancedCombatItemOverrides.register();
            });
        }
        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event)
        {
            event.registerItem(new WavehammerClientExtension(), NuancedCombatItems.WAVEHAMMER);
        }
        @SubscribeEvent
        public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event)
        {
            event.register((itemStack, tintIndex) -> {
                PotionBundleContents contents = ((PotionBundleContents)itemStack.getOrDefault(NuancedCombatComponents.POTION_BUNDLE_CONTENTS, PotionBundleContents.EMPTY));
                if (contents.potions().isEmpty()) return -1;
                if (tintIndex > 0 && contents.potions().size() >= (tintIndex - 1) && tintIndex <= 3){
                    return FastColor.ARGB32.opaque(PotionContents.getColor(contents.potions().get(tintIndex - 1)));
                }
                return -1;
            }, NuancedCombatItems.POTION_BUNDLE);
        }
    }
}
