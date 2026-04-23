package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;
import static com.sward.gregictinkering.GregicTinkeringMaterialIds.POLYBENZIMIDAZOLE;
import static com.sward.gregictinkering.GregicTinkeringMaterialIds.POLYTETRAFLUOROETHYLENE;
import static com.sward.gregictinkering.GregicTinkeringMaterialIds.STYRENE_BUTADIENE_RUBBER;
import static com.sward.gregictinkering.data.TieredMaterialIds.*;

public class GregicMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider
{
	public GregicMaterialRenderInfoProvider(
		PackOutput packOutput,
		AbstractMaterialSpriteProvider spriteProvider,
		ExistingFileHelper existingFileHelper
	)
	{
		super(packOutput, spriteProvider, existingFileHelper);
	}

	@Override
	protected void addMaterialRenderInfo()
	{
		buildRenderInfo(IMaterial.UNKNOWN_ID);

		buildRenderInfo(RUBBER).color(0xFF54403D);
		buildRenderInfo(POLYETHYLENE).color(0xFFC8C8C8);
		buildRenderInfo(SILICONE_RUBBER).color(0xFFF0F0F0);
		buildRenderInfo(STYRENE_BUTADIENE_RUBBER).color(0xFF34312B);
		buildRenderInfo(POLYTETRAFLUOROETHYLENE).color(0xFF6E6E6E);
		buildRenderInfo(POLYBENZIMIDAZOLE).color(0xFF464441);

		addUntinted(ENGINES);
		addUntinted(BATTERIES);
		addUntinted(TANKS);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Material Render Info Provider";
	}

	private void addUntinted(MaterialId[][] materials)
	{
		for (MaterialId[] tier : materials)
		{
			for (MaterialId materialId : tier)
			{
				buildRenderInfo(materialId).color(0xFFFFFFFF);
			}
		}
	}
}
