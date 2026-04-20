package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.GregicStatlessMaterialStats;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class CasingMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider
{
	public CasingMaterialStatsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials)
	{
		super(packOutput, materials);
	}

	@Override
	protected void addMaterialStats()
	{
		for (int i = 0; i < CasingMaterialDataProvider.CASINGS.length; i++)
		{
			for (MaterialId metalId : CasingMaterialDataProvider.CASINGS[i])
			{
				addMaterialStats(
					metalId,
					GregicStatlessMaterialStats.CASING
				);
			}
		}
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Casing Material Stats";
	}
}
