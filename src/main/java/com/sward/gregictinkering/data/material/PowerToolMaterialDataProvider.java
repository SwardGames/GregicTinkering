package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.sward.gregictinkering.data.TieredMaterialIds.*;

public class PowerToolMaterialDataProvider extends AbstractMaterialDataProvider
{
	public PowerToolMaterialDataProvider(PackOutput packOutput)
	{
		super(packOutput);
	}

	@Override
	protected void addMaterials()
	{
		addTiered(ENGINES);
		addTiered(TANKS);
		addTiered(BATTERIES);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Power Tool Materials";
	}

	private void addTiered(MaterialId[][] materials)
	{
		for (int i = 0; i < materials.length; i++)
		{
			for (MaterialId materialId : materials[i])
			{
				addMaterial(materialId, i, ORDER_COMPAT + 1, false);
			}
		}
	}
}
