package com.sward.gregictinkering.data.material;

import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;

public class CraftingToolMaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider
{
	public CraftingToolMaterialTraitsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials)
	{
		super(packOutput, materials);
	}

	@Override
	protected void addMaterialTraits()
	{
		addDefaultTraits(RUBBER, TinkerModifiers.bonking);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Material Traits";
	}
}
