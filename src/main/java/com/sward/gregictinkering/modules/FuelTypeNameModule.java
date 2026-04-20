package com.sward.gregictinkering.modules;

import com.sward.gregictinkering.GregicTinkeringToolStats;
import com.sward.gregictinkering.materials.stats.FuelType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.definition.module.ToolModule;
import slimeknights.tconstruct.library.tools.definition.module.display.MaterialToolName;
import slimeknights.tconstruct.library.tools.definition.module.display.ToolNameHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class FuelTypeNameModule implements ToolNameHook, ToolModule
{
	public static final FuelTypeNameModule INSTANCE = new FuelTypeNameModule();

	private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.<FuelTypeNameModule>defaultHooks(ToolHooks.DISPLAY_NAME);

	public static final RecordLoadable<FuelTypeNameModule> LOADER = new SingletonLoader<>(INSTANCE);

	@Override
	public @NotNull List<ModuleHook<?>> getDefaultHooks()
	{
		return DEFAULT_HOOKS;
	}

	@Override
	public @NotNull RecordLoadable<FuelTypeNameModule> getLoader()
	{
		return LOADER;
	}

	@Override
	public @NotNull Component getDisplayName(
		@NotNull ToolDefinition definition,
		@NotNull ItemStack stack,
		@Nullable IToolStackView tool
	)
	{
		tool = ToolNameHook.getTool(stack, tool);

		Component itemName = Component.translatable(stack.getItem().getDescriptionId());

		FuelType fuelType = tool.getStats().get(GregicTinkeringToolStats.FUEL_TYPE);

		itemName = Component.translatable("item.gregic_tinkering.powered." + fuelType.toString().toLowerCase(Locale.ROOT), itemName);

		MaterialVariantId material = tool.getMaterials().get(0).getVariant();

		if (IMaterial.UNKNOWN_ID.equals(material))
		{
			return itemName;
		}

		return MaterialToolName.nameForMaterial(material, itemName);
	}
}
