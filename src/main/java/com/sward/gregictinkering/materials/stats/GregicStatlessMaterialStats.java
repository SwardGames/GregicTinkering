package com.sward.gregictinkering.materials.stats;

import com.sward.gregictinkering.GregicTinkeringMod;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

public enum GregicStatlessMaterialStats implements IMaterialStats
{
	CASING("casing");

	private static final List<Component> LOCALIZED = List.of(IMaterialStats.makeTooltip(TConstruct.getResource("extra.no_stats")));
	private static final List<Component> DESCRIPTION = List.of(Component.empty());

	@Getter
	private final MaterialStatType<GregicStatlessMaterialStats> type;

	// no stats

	GregicStatlessMaterialStats(String name)
	{
		this.type = MaterialStatType.singleton(new MaterialStatsId(GregicTinkeringMod.id(name)), this);
	}

	@Override
	public @NotNull List<Component> getLocalizedInfo()
	{
		return LOCALIZED;
	}

	@Override
	public @NotNull List<Component> getLocalizedDescriptions()
	{
		return DESCRIPTION;
	}

	@Override
	public void apply(@NotNull ModifierStatsBuilder builder, float scale) { }
}
