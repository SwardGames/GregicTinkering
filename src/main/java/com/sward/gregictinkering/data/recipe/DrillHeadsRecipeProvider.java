package com.sward.gregictinkering.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.Collection;

public class DrillHeadsRecipeProvider extends ShapedToolPartRecipeProvider
{
	public DrillHeadsRecipeProvider(
		PackOutput packOutput,
		Collection<MaterialId> materials
	)
	{
		super(packOutput, GregicTinkeringToolParts.DRILL_HEAD, materials, "drill_heads");
	}

	@Override
	protected void buildPattern(JsonArray pattern)
	{
		pattern.add("PPW");
		pattern.add("PPP");
		pattern.add("HPG");
	}

	@Override
	protected void buildKey(JsonObject key, MaterialId materialId)
	{
		String path = materialId.getPath();

		key.add("P", tagIngredient("forge:plates/" + path));
		key.add("G", tagIngredient("forge:small_gears/" + path));
		key.add("W", tagIngredient("forge:tools/wrench"));
		key.add("H", tagIngredient("forge:tools/hammers"));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Drill Head Recipes";
	}
}
