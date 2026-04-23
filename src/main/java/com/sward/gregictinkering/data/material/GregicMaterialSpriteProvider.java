package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.BatteryMaterialStats;
import com.sward.gregictinkering.materials.stats.EngineMaterialStats;
import com.sward.gregictinkering.materials.stats.PlungerHeadMaterialStats;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;
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
		buildMaterial(RUBBER).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0x7F, 0xFF241520).addARGB(0xFF, 0xFF54403D).build()
		);

		buildMaterial(POLYETHYLENE).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0xFF, 0xFFC8C8C8).build()
		);

		buildMaterial(SILICONE_RUBBER).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0x7F, 0xFFE8E8E0).addARGB(0xFF, 0xFFF0F0F0).build()
		);

		buildMaterial(STYRENE_BUTADIENE_RUBBER).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0x7F, 0xFF110B09).addARGB(0xFF, 0xFF34312B).build()
		);

		buildMaterial(POLYTETRAFLUOROETHYLENE).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0x7F, 0xFF202020).addARGB(0xFF, 0xFF6E6E6E).build()
		);

		buildMaterial(POLYBENZIMIDAZOLE).statType(PlungerHeadMaterialStats.ID).colorMapper(
			GreyToColorMapping.builderFromBlack().addARGB(0x7F, 0xFF382E1B).addARGB(0xFF, 0xFF464441).build()
		);

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
