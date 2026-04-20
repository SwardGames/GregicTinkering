package com.sward.gregictinkering.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.Collection;

public class ChainsawHeadsRecipeProvider extends ShapedToolPartRecipeProvider
{
	public ChainsawHeadsRecipeProvider(
		PackOutput packOutput,
		Collection<MaterialId> materials
	)
	{
		super(packOutput, GregicTinkeringToolParts.CHAINSAW_HEAD, materials, "chainsaw_heads");
	}

	@Override
	protected void buildPattern(JsonArray pattern)
	{
		pattern.add("RRW");
		pattern.add("RPR");
		pattern.add("HRG");
	}

	@Override
	protected void buildKey(JsonObject key, MaterialId materialId)
	{
		String path = materialId.getPath();

		key.add("R", tagIngredient("forge:rings/" + path));
		key.add("P", tagIngredient("forge:plates/" + path));
		key.add("G", tagIngredient("forge:small_gears/" + path));
		key.add("W", tagIngredient("forge:tools/wrench"));
		key.add("H", tagIngredient("forge:tools/hammers"));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Chainsaw Head Recipes";
	}
}
