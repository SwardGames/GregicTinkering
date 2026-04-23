package com.sward.gregictinkering.materials.stats;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.BooleanLoadable;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.primitive.LongLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.ArrayList;
import java.util.List;

import static slimeknights.tconstruct.library.materials.stats.IMaterialStats.makeTooltipKey;

/** Stats for power tool batteries and tanks */
public record BatteryMaterialStats(long capacity, int powerTier, boolean isTank) implements IMaterialStats
{
	public static final MaterialStatsId ID = new MaterialStatsId(GregicTinkeringMod.id("battery"));
	public static final MaterialStatType<BatteryMaterialStats> TYPE = new MaterialStatType<>(
		ID,
		new BatteryMaterialStats(1024L, 0, false),
		RecordLoadable.create(
			LongLoadable.ANY.defaultField("capacity", 0L, true, BatteryMaterialStats::capacity),
			IntLoadable.ANY_FULL.defaultField("power_tier", 0, true, BatteryMaterialStats::powerTier),
			BooleanLoadable.DEFAULT.defaultField("is_tank", false, true, BatteryMaterialStats::isTank),
			BatteryMaterialStats::new
		)
	);

	// tooltip prefixes
	private static final String CAPACITY_PREFIX = makeTooltipKey(GregicTinkeringMod.id("capacity"));
	private static final String FLUID_CAPACITY_PREFIX = makeTooltipKey(GregicTinkeringMod.id("fluid_capacity"));

	// multipliers

	@Override
	public @NotNull MaterialStatType<BatteryMaterialStats> getType()
	{
		return TYPE;
	}

	@Override
	public @NotNull MutableComponent getLocalizedName()
	{
		return Component.translatable(isTank ? "stat.gregic_tinkering.battery.tank" : "stat.gregic_tinkering.battery.battery");
	}

	@Override
	public @NotNull List<Component> getLocalizedInfo()
	{
		List<Component> list = new ArrayList<>();

		if (this.isTank)
		{
			list.add(IToolStat.formatNumber(FLUID_CAPACITY_PREFIX, TextColor.fromRgb(0xFFDDBB4F), this.capacity));
		}
		else
		{
			list.add(
				this.capacity == Long.MAX_VALUE
					? Component.translatable(CAPACITY_PREFIX).append(Component.literal("∞").withStyle(style -> style.withColor(0xFF54FC54)))
					: IToolStat.formatNumber(CAPACITY_PREFIX, TextColor.fromRgb(0xFF54FC54), this.capacity)
			);

			list.add(ToolElectricItemCapability.POWER_TIER_STAT.formatValue(this.powerTier));
		}

		return list;
	}

	@Override
	public @NotNull List<Component> getLocalizedDescriptions()
	{
		List<Component> list = new ArrayList<>();

		if (this.isTank)
		{
			list.add(IMaterialStats.makeTooltip(GregicTinkeringMod.id("battery.fluid_capacity.description")));
		}
		else
		{
			list.add(IMaterialStats.makeTooltip(GregicTinkeringMod.id("battery.capacity.description")));
			list.add(IMaterialStats.makeTooltip(GregicTinkeringMod.id("battery.power_tier.description")));
		}

		return list;
	}

	@Override
	public void apply(@NotNull ModifierStatsBuilder builder, float scale)
	{
		if (this.isTank)
		{
			ToolTankHelper.CAPACITY_STAT.add(builder, this.capacity * scale);
		}
		else
		{
			ToolElectricItemCapability.MAX_CHARGE_STAT.add(builder, (long)(this.capacity * scale));
			ToolElectricItemCapability.POWER_TIER_STAT.update(builder, this.powerTier);
		}
	}
}
