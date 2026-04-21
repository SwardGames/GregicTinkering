package com.sward.gregictinkering.materials.stats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.sward.gregictinkering.GregicTinkeringMod;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IRepairableMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import java.util.List;

public record PlungerHeadMaterialStats(int durability, float attack) implements IRepairableMaterialStats
{
	public static final MaterialStatsId ID = new MaterialStatsId(GregicTinkeringMod.id("plunger_head"));
	public static final MaterialStatType<PlungerHeadMaterialStats> TYPE = new MaterialStatType<>(
		ID, new PlungerHeadMaterialStats(1, 1F), RecordLoadable.create(
		IRepairableMaterialStats.DURABILITY_FIELD,
		FloatLoadable.FROM_ZERO.defaultField("melee_attack", 1F, true, PlungerHeadMaterialStats::attack),
		PlungerHeadMaterialStats::new
	)
	);

	// tooltip descriptions
	private static final List<Component> DESCRIPTION = ImmutableList.of(
		ToolStats.DURABILITY.getDescription(),
		ToolStats.HARVEST_TIER.getDescription(),
		ToolStats.MINING_SPEED.getDescription(),
		ToolStats.ATTACK_DAMAGE.getDescription()
	);

	@Override
	public @NotNull MaterialStatType<?> getType()
	{
		return TYPE;
	}

	@Override
	public @NotNull List<Component> getLocalizedInfo()
	{
		List<Component> info = Lists.newArrayList();

		info.add(ToolStats.DURABILITY.formatValue(this.durability));
		info.add(ToolStats.ATTACK_DAMAGE.formatValue(this.attack));

		return info;
	}

	@Override
	public @NotNull List<Component> getLocalizedDescriptions()
	{
		return DESCRIPTION;
	}

	@Override
	public void apply(@NotNull ModifierStatsBuilder builder, float scale)
	{
		// update for floats cancels out the base stats the first time used, makes the behavior more predictable between this and the stats module
		ToolStats.DURABILITY.update(builder, durability * scale);
		ToolStats.ATTACK_DAMAGE.update(builder, attack * scale);
	}
}