package com.sward.gregictinkering.materials.stats;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringToolStats;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static slimeknights.tconstruct.library.materials.stats.IMaterialStats.makeTooltipKey;

/** Stats for power tool engines */
public record EngineMaterialStats(float efficiency, int powerDraw, float powerReinforcement, float miningSpeed, FuelType fuelType) implements IMaterialStats
{
	public static final MaterialStatsId ID = new MaterialStatsId(GregicTinkeringMod.id("engine"));
	public static final MaterialStatType<EngineMaterialStats> TYPE = new MaterialStatType<>(
		ID,
		new EngineMaterialStats(1F, 0, 0.8F, 0F, FuelType.EU),
		RecordLoadable.create(
			FloatLoadable.ANY.defaultField("efficiency", 1F, true, EngineMaterialStats::efficiency),
			IntLoadable.ANY_FULL.defaultField("power_draw", 0, true, EngineMaterialStats::powerDraw),
			FloatLoadable.ANY.defaultField("power_reinforcement", 0.8F, true, EngineMaterialStats::powerReinforcement),
			FloatLoadable.ANY.defaultField("mining_speed", 0F, true, EngineMaterialStats::miningSpeed),
			FuelType.LOADABLE.defaultField("fuel_type", FuelType.EU, true, EngineMaterialStats::fuelType),
			EngineMaterialStats::new
		)
	);

	// tooltip prefixes
	private static final String FUEL_TYPE_PREFIX = makeTooltipKey(GregicTinkeringMod.id("fuel_type"));
	private static final String STEAM_TANK_MULTIPLIER = makeTooltipKey(GregicTinkeringMod.id("steam_tank_multiplier"));

	// multipliers

	@Override
	public @NotNull MaterialStatType<EngineMaterialStats> getType()
	{
		return TYPE;
	}

	@Override
	public @NotNull List<Component> getLocalizedInfo()
	{
		List<Component> list = new ArrayList<>();

		if (this.fuelType != FuelType.EU)
		{
			list.add(IToolStat.formatColored(GregicTinkeringToolStats.EFFICIENCY.getTranslationKey(), this.efficiency, -0.5F, Util.PERCENT_FORMAT));
		}

		list.add(GregicTinkeringToolStats.POWER_DRAW.formatValue(this.powerDraw));
		list.add(IToolStat.formatColoredPercentBoost(ToolStats.MINING_SPEED.getTranslationKey(), this.miningSpeed));
		list.add(IToolStat.formatColored(GregicTinkeringToolStats.POWER_REINFORCEMENT.getTranslationKey(), this.powerReinforcement, -0.5F, Util.PERCENT_FORMAT));
		list.add(Component.translatable(getFuelTypeKey()));

		if (this.fuelType == FuelType.STEAM)
		{
			list.add(Component.translatable(STEAM_TANK_MULTIPLIER));
		}

		return list;
	}

	@Override
	public @NotNull List<Component> getLocalizedDescriptions()
	{
		List<Component> list = new ArrayList<>();

		if (this.fuelType != FuelType.EU)
		{
			list.add(GregicTinkeringToolStats.EFFICIENCY.getDescription());
		}

		list.add(GregicTinkeringToolStats.POWER_DRAW.getDescription());
		list.add(ToolStats.MINING_SPEED.getDescription());
		list.add(GregicTinkeringToolStats.POWER_REINFORCEMENT.getDescription());
		list.add(Component.translatable(getFuelTypeKey() + ".description"));

		if (this.fuelType == FuelType.STEAM)
		{
			list.add(Component.translatable(STEAM_TANK_MULTIPLIER + ".description"));
		}

		return list;
	}

	@Override
	public void apply(@NotNull ModifierStatsBuilder builder, float scale)
	{
		GregicTinkeringToolStats.EFFICIENCY.add(builder, this.efficiency - 1.0F);
		GregicTinkeringToolStats.POWER_DRAW.add(builder, this.powerDraw * scale);
		GregicTinkeringToolStats.POWER_REINFORCEMENT.add(builder, this.powerReinforcement);
		GregicTinkeringToolStats.FUEL_TYPE.update(builder, this.fuelType);
		ToolStats.MINING_SPEED.percent(builder, this.miningSpeed * scale);

		if (this.fuelType == FuelType.STEAM)
		{
			ToolTankHelper.CAPACITY_STAT.multiply(builder, 100F);
		}
	}

	private String getFuelTypeKey()
	{
		return FUEL_TYPE_PREFIX + "." + this.fuelType.toString().toLowerCase(Locale.ROOT);
	}
}
