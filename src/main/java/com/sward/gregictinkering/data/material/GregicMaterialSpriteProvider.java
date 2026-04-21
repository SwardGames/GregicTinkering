package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.BatteryMaterialStats;
import com.sward.gregictinkering.materials.stats.EngineMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import static com.sward.gregictinkering.data.TieredMaterialIds.*;

public class GregicMaterialSpriteProvider extends AbstractMaterialSpriteProvider
{
	private static final GreyToColorMapping INVALID_COLOR_MAPPING = GreyToColorMapping
		.builderFromBlack()
		.addARGB(0xFF, 0xFFFF00FF)
		.build();

	@Override
	protected void addAllMaterials()
	{
		addInvalid(ENGINES, EngineMaterialStats.ID);
		addInvalid(BATTERIES, BatteryMaterialStats.ID);
		addInvalid(TANKS, BatteryMaterialStats.ID);
	}

	private void addInvalid(MaterialId[][] materials, MaterialStatsId... stats)
	{
		for (MaterialId[] material : materials)
		{
			for (MaterialId materialId : material)
			{
				buildMaterial(materialId).statType(stats).colorMapper(INVALID_COLOR_MAPPING);
			}
		}
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Material Sprite Provider";
	}
}
