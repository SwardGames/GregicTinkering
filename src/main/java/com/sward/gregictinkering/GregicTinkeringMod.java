package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;

import java.util.Set;

@Mod(GregicTinkeringMod.MOD_ID)
public class GregicTinkeringMod
{
	public static final String MOD_ID = "gregic_tinkering";

	@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger();

	public static final GTRegistrate REGISTRATE = GTRegistrate.create(MOD_ID);

	public GregicTinkeringMod(@NotNull FMLJavaModLoadingContext context)
	{
		IEventBus modEventBus = context.getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(TinkerGTMaterials::register);

		modEventBus.addListener(GregicTinkeringMod::modifyMaterials);

		GregicTinkeringItems.ITEMS.register(modEventBus);
		GregicTinkeringToolParts.ITEMS.register(modEventBus);
		GregicTinkeringTools.ITEMS.register(modEventBus);
		GregicTinkeringModifiers.MODIFIERS.register(modEventBus);
		GregicTinkeringRecipes.RECIPE_SERIALIZERS.register(modEventBus);
		GregicTinkeringCreativeTabs.CREATIVE_TABS.register(modEventBus);

		modEventBus.addListener(GregicTinkeringToolStats::register);

		modEventBus.addListener(GregicTinkeringConfig::onModConfig);
		context.registerConfig(ModConfig.Type.COMMON, GregicTinkeringConfig.SPEC);

		REGISTRATE.registerRegistrate();
	}

	public static ResourceLocation id(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
		ToolCapabilityProvider.register((stack, tool) -> new ToolElectricItemCapability.Provider(tool));
	}

	private static void modifyMaterials(PostMaterialEvent event)
	{
		MaterialFlag[] materialFlags = {
			MaterialFlags.GENERATE_SMALL_GEAR,
			MaterialFlags.GENERATE_RING,
			MaterialFlags.GENERATE_BOLT_SCREW
		};
		
		GTMaterials.Copper.addFlags(materialFlags);
		GTMaterials.Cobalt.addFlags(materialFlags);
		GTMaterials.Osmium.addFlags(materialFlags);
		GTMaterials.Lead.addFlags(materialFlags);
		GTMaterials.Silver.addFlags(materialFlags);
		GTMaterials.RoseGold.addFlags(materialFlags);
		GTMaterials.Electrum.addFlags(materialFlags);
		GTMaterials.Invar.addFlags(materialFlags);

		if (ModList.get().isLoaded("gm_construct"))
		{
			GregicTinkeringMod.LOGGER.info("Greg's Modern Construct found - registering tool material flags.");

			Set<Material> invalidToolMaterials = Set.of(
				GTMaterials.Bronze,
				GTMaterials.Cobalt,
				GTMaterials.Copper,
				GTMaterials.Diamond,
				GTMaterials.Flint,
				GTMaterials.Invar,
				GTMaterials.Iron,
				GTMaterials.Netherite,
				GTMaterials.RoseGold,
				GTMaterials.Steel
			);

			for (Material material : GTCEuAPI.materialManager.getRegisteredMaterials())
			{
				if (invalidToolMaterials.contains(material))
				{
					continue;
				}

				if (!material.hasProperty(PropertyKey.TOOL))
				{
					continue;
				}

				if (material.getProperty(PropertyKey.TOOL).hasType(GTToolType.PICKAXE))
				{
					material.addFlags(MaterialFlags.GENERATE_SMALL_GEAR, MaterialFlags.GENERATE_RING);
				}
			}
		}
	}
}