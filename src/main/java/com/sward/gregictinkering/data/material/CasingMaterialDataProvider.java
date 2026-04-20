package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.data.TieredMaterialIds;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class CasingMaterialDataProvider extends AbstractMaterialDataProvider
{
	public static final MaterialId[][] CASINGS = TieredMaterialIds.METALS;

	public CasingMaterialDataProvider(PackOutput packOutput)
	{
		super(packOutput);
	}

	@Override
	protected void addMaterials()
	{
		for (int i = 0; i < CASINGS.length; i++)
		{
			for (MaterialId metalId : CASINGS[i])
			{
				addMaterial(metalId, i, ORDER_GENERAL, false);
			}
		}
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Casing Materials";
	}
}
