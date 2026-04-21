package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

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
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Materials";
	}
}
