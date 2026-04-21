package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

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
