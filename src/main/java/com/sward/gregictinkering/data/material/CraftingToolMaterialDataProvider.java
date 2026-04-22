package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;

public class CraftingToolMaterialDataProvider extends AbstractMaterialDataProvider
{
	public CraftingToolMaterialDataProvider(PackOutput packOutput)
	{
		super(packOutput);
	}

	@Override
	protected void addMaterials()
	{
		addMaterial(RUBBER, 0, ORDER_COMPAT, true);
		addMaterial(POLYETHYLENE, 1, ORDER_COMPAT, true);
		addMaterial(SILICONE_RUBBER, 2, ORDER_COMPAT, true);
		addMaterial(STYRENE_BUTADIENE_RUBBER, 2, ORDER_COMPAT, true);
		addMaterial(POLYTETRAFLUOROETHYLENE, 2, ORDER_COMPAT, true);
		addMaterial(POLYBENZIMIDAZOLE, 3, ORDER_COMPAT, true);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Materials";
	}
}
