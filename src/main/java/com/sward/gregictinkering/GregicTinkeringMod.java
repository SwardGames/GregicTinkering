package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;

@Mod(GregicTinkeringMod.MOD_ID)
@SuppressWarnings("removal")
public class GregicTinkeringMod
{
	public static final String MOD_ID = "gregic_tinkering";
	public static final Logger LOGGER = LogManager.getLogger();

	public GregicTinkeringMod()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(TinkerGTMaterials::register);

		GregicTinkeringItems.ITEMS.register(modEventBus);
		GregicTinkeringToolParts.ITEMS.register(modEventBus);
		GregicTinkeringTools.ITEMS.register(modEventBus);
		GregicTinkeringModifiers.MODIFIERS.register(modEventBus);

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