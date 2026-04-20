package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(GregicTinkeringMod.MOD_ID)
@SuppressWarnings("removal")
public class GregicTinkeringMod
{
	public static final String MOD_ID = "gregic_tinkering";
	public static final Logger LOGGER = LogManager.getLogger();

	private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

	public static final RegistryObject<CreativeModeTab> TOOL_PARTS_TAB = CREATIVE_TABS.register(
		"tool_parts",
		() -> CreativeModeTab
			.builder()
			.title(Component.translatable("item_group." + MOD_ID + ".tool_parts"))
			.icon(() -> new ItemStack(GregicTinkeringToolParts.DRILL_HEAD.get()))
			.displayItems(
				(params, output) ->
				{
					accept(output::accept, GregicTinkeringToolParts.WRENCH_HEAD);
					accept(output::accept, GregicTinkeringToolParts.DRILL_HEAD);
					accept(output::accept, GregicTinkeringToolParts.CHAINSAW_HEAD);
					accept(output::accept, GregicTinkeringToolParts.CASING);
					accept(output::accept, GregicTinkeringToolParts.ENGINE);
					accept(output::accept, GregicTinkeringToolParts.BATTERY);
				}
			)
			.build()
	);

	public static final RegistryObject<CreativeModeTab> TOOLS_TAB = CREATIVE_TABS.register(
		"tools",
		() -> CreativeModeTab
			.builder()
			.title(Component.translatable("item_group." + MOD_ID + ".tools"))
			.icon(() -> new ItemStack(GregicTinkeringTools.DRILL.get()))
			.displayItems(
				(params, output) ->
				{
					output.accept(GregicTinkeringTools.WRENCH.tool.get());
					output.accept(GregicTinkeringTools.WRENCH.tool.get());
					output.accept(GregicTinkeringTools.HAMMER.tool.get());
					output.accept(GregicTinkeringTools.FILE.tool.get());
					output.accept(GregicTinkeringTools.SCREWDRIVER.tool.get());
					output.accept(GregicTinkeringTools.SAW.tool.get());
					output.accept(GregicTinkeringTools.WIRE_CUTTER.tool.get());
					output.accept(GregicTinkeringTools.CROWBAR.tool.get());
					output.accept(GregicTinkeringTools.SOFT_MALLET.tool.get());
					output.accept(GregicTinkeringTools.PLUNGER.tool.get());
					output.accept(GregicTinkeringTools.DRILL.get());
					output.accept(GregicTinkeringTools.CHAINSAW.get());
				}
			)
			.build()
	);

	/** Adds a tool part to the tab */
	private static void accept(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item) {
		item.get().addVariants(output, "");
	}

	public GregicTinkeringMod()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(TinkerGTMaterials::register);

		GregicTinkeringItems.ITEMS.register(modEventBus);
		GregicTinkeringToolParts.ITEMS.register(modEventBus);
		GregicTinkeringTools.ITEMS.register(modEventBus);
		GregicTinkeringModifiers.MODIFIERS.register(modEventBus);

		CREATIVE_TABS.register(modEventBus);

		modEventBus.register(new GregicTinkeringToolStats());

		// Most other events are fired on Forge's bus.
		// If we want to use annotations to register event listeners,
		// we need to register our object like this!
		// MinecraftForge.EVENT_BUS.register(this);
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