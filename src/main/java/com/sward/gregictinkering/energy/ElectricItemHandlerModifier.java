package com.sward.gregictinkering.energy;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.client.util.TooltipHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;

public final class ElectricItemHandlerModifier extends NoLevelsModifier implements ValidateModifierHook, ModifierRemovalHook
{
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(this, ModifierHooks.VALIDATE, ModifierHooks.REMOVE);
	}

	@Override
	public @NotNull Component getDisplayName(@NotNull IToolStackView tool, @NotNull ModifierEntry entry, @Nullable RegistryAccess access)
	{
		long charge = ToolElectricItemCapability.getCharge(tool);
		long maxCharge = ToolElectricItemCapability.getMaxCharge(tool);
		int tier = ToolElectricItemCapability.getTier(tool);

		Component maxChargeComponent = maxCharge == Long.MAX_VALUE
			? Component.literal("∞")
			: Component.literal(Util.COMMA_FORMAT.format(maxCharge));

		Component tierComponent = tier == 30
			? Component.literal("∞").withStyle(TooltipHelper.RAINBOW_HSL_FAST)
			: Component.literal(GTValues.VNF[tier]);

		return Component.translatable(
			"metaitem.generic.electric_item.tooltip",
			Component.literal(Util.COMMA_FORMAT.format(charge)),
			maxChargeComponent,
			tierComponent
		).withStyle(style -> style.withColor(getColor()));
	}

	@Override
	public int getPriority()
	{
		return 500;
	}

	@Override
	@Nullable
	public Component validate(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier)
	{
		ToolElectricItemCapability.checkCharge(tool);
		return null;
	}

	@Override
	@Nullable
	public Component onRemoved(@NotNull IToolStackView tool, @NotNull Modifier modifier)
	{
		ToolElectricItemCapability.checkCharge(tool);
		return null;
	}
}
