package com.feliscape.nuanced_combat;

import com.feliscape.nuanced_combat.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NuancedCombat.MOD_ID)
public class NuancedCombat
{
    public static final String MOD_ID = "nuanced_combat";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NuancedCombat(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NCClientConfig.SPEC);

        NuancedCombatItems.register(modEventBus);
        NuancedCombatComponents.register(modEventBus);
        NuancedCombatEntityTypes.register(modEventBus);

        NuancedCombatRecipeTypes.register(modEventBus);
        NuancedCombatRecipeSerializers.register(modEventBus);

        NuancedCombatMobEffects.register(modEventBus);
        NuancedCombatDataAttachments.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(NuancedCombatCreativeModeTabs::addCreative);
    }

    public static ResourceLocation location(String path){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
}
