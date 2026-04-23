package com.sward.gregictinkering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.sward.gregictinkering.GregicTinkeringMod.*;
import static com.sward.gregictinkering.GregicTinkeringItems.*;
import static com.sward.gregictinkering.GregicTinkeringToolParts.*;
import static com.sward.gregictinkering.GregicTinkeringTools.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public final class GregicTinkeringCreativeTabs
{
	static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

	public static final RegistryObject<CreativeModeTab> TOOL_PARTS_TAB = CREATIVE_TABS.register(
		"tool_parts",
		() -> CreativeModeTab
			.builder()
			.title(Component.translatable("item_group." + MOD_ID + ".tool_parts"))
			.icon(
				() ->
				{
					MaterialVariantId material;

					if (MaterialRegistry.isFullyLoaded())
					{
						material = ToolBuildHandler.RANDOM.getMaterial(HeadMaterialStats.ID, RandomSource.create());
					}
					else
					{
						material = ToolBuildHandler.getRenderMaterial(0);
					}

					return DRILL_HEAD.get().withMaterialForDisplay(material);
				}
			)
			.displayItems(
				(params, output) ->
				{
					// Shapes
					output.accept(WRENCH_HEAD_SHAPE.get());
					output.accept(HAMMER_HEAD_SHAPE.get());
					output.accept(FILE_HEAD_SHAPE.get());
					output.accept(SCREWDRIVER_HEAD_SHAPE.get());
					output.accept(SAW_HEAD_SHAPE.get());
					output.accept(WIRE_CUTTER_HEAD_SHAPE.get());
					output.accept(CROWBAR_HEAD_SHAPE.get());

					// Casts
					output.accept(WRENCH_HEAD_CAST);
					output.accept(HAMMER_HEAD_CAST);
					output.accept(FILE_HEAD_CAST);
					output.accept(SCREWDRIVER_HEAD_CAST);
					output.accept(SAW_HEAD_CAST);
					output.accept(WIRE_CUTTER_HEAD_CAST);
					output.accept(CROWBAR_HEAD_CAST);

					// Sand
					output.accept(WRENCH_HEAD_CAST::getSand);
					output.accept(HAMMER_HEAD_CAST::getSand);
					output.accept(FILE_HEAD_CAST::getSand);
					output.accept(SCREWDRIVER_HEAD_CAST::getSand);
					output.accept(SAW_HEAD_CAST::getSand);
					output.accept(WIRE_CUTTER_HEAD_CAST::getSand);
					output.accept(CROWBAR_HEAD_CAST::getSand);

					// Red Sand
					output.accept(WRENCH_HEAD_CAST::getRedSand);
					output.accept(HAMMER_HEAD_CAST::getRedSand);
					output.accept(FILE_HEAD_CAST::getRedSand);
					output.accept(SCREWDRIVER_HEAD_CAST::getRedSand);
					output.accept(SAW_HEAD_CAST::getRedSand);
					output.accept(WIRE_CUTTER_HEAD_CAST::getRedSand);
					output.accept(CROWBAR_HEAD_CAST::getRedSand);

					// Crafting Tool Heads
					accept(output::accept, WRENCH_HEAD);
					accept(output::accept, HAMMER_HEAD);
					accept(output::accept, FILE_HEAD);
					accept(output::accept, SCREWDRIVER_HEAD);
					accept(output::accept, SAW_HEAD);
					accept(output::accept, WIRE_CUTTER_HEAD);
					accept(output::accept, CROWBAR_HEAD);
					accept(output::accept, SOFT_MALLET_HEAD);
					accept(output::accept, PLUNGER_HEAD);

					// Power Tool Heads
					accept(output::accept, DRILL_HEAD);
					accept(output::accept, CHAINSAW_HEAD);

					// Power Tool Parts
					accept(output::accept, CASING);
					accept(output::accept, ENGINE);
					accept(output::accept, BATTERY);
				}
			)
			.build()
	);

	public static final RegistryObject<CreativeModeTab> TOOLS_TAB = CREATIVE_TABS.register(
		"tools",
		() -> CreativeModeTab
			.builder()
			.title(Component.translatable("item_group." + MOD_ID + ".tools"))
			.icon(() -> DRILL.get().getRenderTool())
			.displayItems(
				(params, output) ->
				{
					// Crafting Tools
					output.accept(WRENCH.get());
					output.accept(HAMMER.get());
					output.accept(FILE.get());
					output.accept(SCREWDRIVER.get());
					output.accept(SAW.get());
					output.accept(WIRE_CUTTER.get());
					output.accept(CROWBAR.get());
					output.accept(SOFT_MALLET.get());
					output.accept(PLUNGER.get());

					// Power Tools
					output.accept(DRILL.get());
					output.accept(CHAINSAW.get());
					output.accept(POWERED_WRENCH.get());
					output.accept(POWERED_HAMMER.get());
					output.accept(POWERED_FILE.get());
					output.accept(POWERED_SCREWDRIVER.get());
					output.accept(POWERED_SAW.get());
					output.accept(POWERED_WIRE_CUTTER.get());
				}
			)
			.build()
	);

	private static void accept(Consumer<ItemStack> output, Supplier<? extends IMaterialItem> item)
	{
		item.get().addVariants(output, "");
	}
}
