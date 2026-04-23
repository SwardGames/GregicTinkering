package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;

@Mod(GregicTinkeringMod.MOD_ID)
public class GregicTinkeringMod
{
	public static final String MOD_ID = "gregic_tinkering";

	@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger();

	public GregicTinkeringMod(@NotNull FMLJavaModLoadingContext context)
	{
		IEventBus modEventBus = context.getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(TinkerGTMaterials::register);

		GregicTinkeringItems.ITEMS.register(modEventBus);
		GregicTinkeringToolParts.ITEMS.register(modEventBus);
		GregicTinkeringTools.ITEMS.register(modEventBus);
		GregicTinkeringModifiers.MODIFIERS.register(modEventBus);
		GregicTinkeringRecipes.RECIPE_SERIALIZERS.register(modEventBus);
		GregicTinkeringCreativeTabs.CREATIVE_TABS.register(modEventBus);

		modEventBus.addListener(GregicTinkeringToolStats::register);
	}

	public static ResourceLocation id(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
		ToolCapabilityProvider.register((stack, tool) -> new ToolElectricItemCapability.Provider(tool));
	}
}