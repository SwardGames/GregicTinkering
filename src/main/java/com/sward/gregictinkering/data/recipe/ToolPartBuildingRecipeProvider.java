package com.sward.gregictinkering.data.recipe;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.partbuilder.PartRecipeBuilder;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

import java.util.function.Consumer;

public class ToolPartBuildingRecipeProvider extends RecipeProvider implements IToolRecipeHelper
{
	public ToolPartBuildingRecipeProvider(PackOutput generator)
	{
		super(generator);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer)
	{
		String partFolder = "parts/";
		String castFolder = "casts/";

		partCasting(consumer, GregicTinkeringToolParts.WRENCH_HEAD.get(), GregicTinkeringToolParts.WRENCH_HEAD_CAST, 4, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.HAMMER_HEAD.get(), GregicTinkeringToolParts.HAMMER_HEAD_CAST, 6, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.FILE_HEAD.get(), GregicTinkeringToolParts.FILE_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.SCREWDRIVER_HEAD.get(), GregicTinkeringToolParts.SCREWDRIVER_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.SAW_HEAD.get(), GregicTinkeringToolParts.SAW_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.WIRE_CUTTER_HEAD.get(), GregicTinkeringToolParts.WIRE_CUTTER_HEAD_CAST, 3, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.CROWBAR_HEAD.get(), GregicTinkeringToolParts.CROWBAR_HEAD_CAST, 2, partFolder, castFolder);

		partBuilding(consumer, GregicTinkeringToolParts.SOFT_MALLET_HEAD.get(), 6, partFolder);
		partBuilding(consumer, GregicTinkeringToolParts.PLUNGER_HEAD.get(), 3, partFolder);
	}

	@Override
	public @NotNull String getModId()
	{
		return GregicTinkeringMod.MOD_ID;
	}

	private void partBuilding(Consumer<FinishedRecipe> consumer, IMaterialItem part, int cost, String partFolder)
	{
		ResourceLocation id = id(part);

		// Part Builder
		PartRecipeBuilder.partRecipe(part)
		                 .setPattern(id)
		                 .setCost(cost)
		                 .save(consumer, location(partFolder + "builder/" + id.getPath()));
	}
}
