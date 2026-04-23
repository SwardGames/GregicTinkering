package com.sward.gregictinkering.modifiers;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.common.data.GTRecipeCapabilities;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.sward.gregictinkering.GregicTinkeringToolStats;
import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import com.sward.gregictinkering.materials.stats.FuelType;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedContext;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;

import java.util.*;

public class PoweredModifier extends NoLevelsModifier implements
	ConditionalStatModifierHook,
	BreakSpeedModifierHook,
	ToolDamageModifierHook,
	TooltipModifierHook
{
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(
			this,
			ModifierHooks.CONDITIONAL_STAT,
			ModifierHooks.BREAK_SPEED,
			ModifierHooks.TOOL_DAMAGE,
			ModifierHooks.TOOLTIP
		);
	}

	@Override
	public float modifyStat(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull LivingEntity living,
		@NotNull FloatToolStat stat,
		float baseValue,
		float multiplier
	)
	{
		if (stat == ToolStats.DRAW_SPEED && !isPowered(tool))
		{
			return 0F;
		}

		return baseValue;
	}

	@Override
	public void onBreakSpeed(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull PlayerEvent.BreakSpeed event,
		@NotNull Direction sideHit,
		boolean isEffective,
		float miningSpeedModifier
	)
	{
		if (isEffective && !isPowered(tool))
		{
			event.setNewSpeed(0F);
		}
	}

	@Override
	public float modifyBreakSpeed(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, @NotNull BreakSpeedContext context, float speed)
	{
		if (context.isEffective() && !isPowered(tool))
		{
			return 0F;
		}

		return BreakSpeedModifierHook.super.modifyBreakSpeed(tool, modifier, context, speed);
	}

	@Override
	public int onDamageTool(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, int amount, @Nullable LivingEntity holder)
	{
		boolean wasPowered = isPowered(tool);

		discharge(tool, amount);

		if (wasPowered && RANDOM.nextFloat() < tool.getStats().get(GregicTinkeringToolStats.POWER_REINFORCEMENT))
		{
			return 0;
		}

		return amount;
	}

	@Override
	public void addTooltip(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@Nullable Player player,
		@NotNull List<Component> tooltip,
		@NotNull TooltipKey tooltipKey,
		@NotNull TooltipFlag tooltipFlag
	)
	{
		tooltip.add(3,
			Component.translatable(
				"modifier.gregic_tinkering.powered.power_draw",
				applyStyle(Component.literal(Util.COMMA_FORMAT.format(getPowerDraw(tool))))
			)
		);

		tooltip.add(
			applyStyle(
				Component.translatable(
					"modifier.gregic_tinkering.powered.reinforcement",
					Util.PERCENT_FORMAT.format(tool.getStats().get(GregicTinkeringToolStats.POWER_REINFORCEMENT))
				)
			)
		);
	}

	public static boolean isPowered(IToolStackView tool)
	{
		if (ToolElectricItemCapability.getCharge(tool) > 0L)
		{
			return true;
		}

		long charge = tryCharge(tool, 0L);

		if (charge != 0L)
		{
			ToolElectricItemCapability.setCharge(tool, charge);

			return true;
		}

		return false;
	}

	private static int getPowerDraw(IToolStackView tool)
	{
		return (int)(tool.getStats().get(GregicTinkeringToolStats.POWER_DRAW) / tool.getStats().get(
			GregicTinkeringToolStats.EFFICIENCY));
	}

	private static void discharge(IToolStackView tool, int amount)
	{
		long charge = ToolElectricItemCapability.getCharge(tool);

		long powerDraw = getPowerDraw(tool);

		charge -= powerDraw * amount;

		if (charge < 0L)
		{
			charge = tryCharge(tool, charge);
		}

		ToolElectricItemCapability.setCharge(tool, Math.max(charge, 0L));
	}

	private static long tryCharge(IToolStackView tool, long charge)
	{
		FuelType fuelType = tool.getStats().get(GregicTinkeringToolStats.FUEL_TYPE);

		if (fuelType == FuelType.EU)
		{
			return charge;
		}

		GTRecipeType recipeType = switch (fuelType)
		{
			case STEAM -> GTRecipeTypes.STEAM_TURBINE_FUELS;
			case DIESEL -> GTRecipeTypes.COMBUSTION_GENERATOR_FUELS;
			case GAS -> GTRecipeTypes.GAS_TURBINE_FUELS;
			default -> null;
		};

		if (recipeType == null)
		{
			return charge;
		}

		FluidStack fluid = ToolTankHelper.TANK_HELPER.getFluid(tool);
		Map<RecipeCapability<?>, List<Object>> inputs = new HashMap<>(1);
		inputs.put(GTRecipeCapabilities.FLUID, ImmutableList.of(fluid));

		GTRecipe recipe = recipeType.db().find(inputs, r -> true);

		if (recipe == null)
		{
			return charge;
		}

		long eu = recipe.duration * recipe.getOutputEUt().getTotalEU();

		long cycles = Math.max(-Math.floorDiv(charge, eu), 1);

		long desiredFuel = ((FluidIngredient) recipe.inputs.get(GTRecipeCapabilities.FLUID).get(0).content).getAmount() * cycles;

		int currentFuel = fluid.getAmount();

		if (currentFuel < desiredFuel)
		{
			ToolTankHelper.TANK_HELPER.setFluid(tool, FluidStack.EMPTY);

			charge += (long) (eu * cycles * (double) currentFuel / desiredFuel);
		}
		else
		{
			if (currentFuel == desiredFuel)
			{
				ToolTankHelper.TANK_HELPER.setFluid(tool, FluidStack.EMPTY);
			}
			else
			{
				ToolTankHelper.TANK_HELPER.setFluid(tool, new FluidStack(fluid, (int) (currentFuel - desiredFuel)));
			}

			charge += eu * cycles;
		}

		return charge;
	}
}
