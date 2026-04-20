package com.sward.gregictinkering.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.Collection;

public class CasingsRecipeProvider extends ShapedToolPartRecipeProvider
{
	public CasingsRecipeProvider(
		PackOutput packOutput,
		Collection<MaterialId> materials
	)
	{
		super(packOutput, GregicTinkeringToolParts.CASING, materials, "casings");
	}

	@Override
	protected void buildPattern(JsonArray pattern)
	{
		pattern.add("PPR");
		pattern.add("STS");
		pattern.add("PPR");
	}

	@Override
	protected void buildKey(JsonObject key, MaterialId materialId)
	{
		String path = materialId.getPath();

		key.add("P", tagIngredient("forge:plates/" + path));
		key.add("R", tagIngredient("forge:rods/" + path));
		key.add("S", tagIngredient("forge:screws/" + path));
		key.add("T", tagIngredient("gtceu:tools/crafting_screwdrivers"));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Casing Recipes";
	}
}
