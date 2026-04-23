package com.sward.gregictinkering.data.recipe;

import com.sward.gregictinkering.*;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;
import slimeknights.tconstruct.library.recipe.partbuilder.PartRecipeBuilder;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

import java.util.function.Consumer;

public class GregicTinkeringRecipeProvider extends RecipeProvider implements IToolRecipeHelper
{
	public GregicTinkeringRecipeProvider(PackOutput generator)
	{
		super(generator);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer)
	{
		toolBuilding(consumer, GregicTinkeringTools.WRENCH, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.HAMMER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.FILE, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.SCREWDRIVER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.SAW, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.WIRE_CUTTER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.CROWBAR, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.SOFT_MALLET, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.PLUNGER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.DRILL, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.CHAINSAW, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_WRENCH, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_HAMMER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_FILE, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_SCREWDRIVER, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_SAW, "tools/");
		toolBuilding(consumer, GregicTinkeringTools.POWERED_WIRE_CUTTER, "tools/");
			
		String partFolder = "parts/";
		String castFolder = "casts/";

		partCasting(consumer, GregicTinkeringToolParts.WRENCH_HEAD.get(), GregicTinkeringToolParts.WRENCH_HEAD_CAST, 4, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.HAMMER_HEAD.get(), GregicTinkeringToolParts.HAMMER_HEAD_CAST, 6, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.FILE_HEAD.get(), GregicTinkeringToolParts.FILE_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.SCREWDRIVER_HEAD.get(), GregicTinkeringToolParts.SCREWDRIVER_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.SAW_HEAD.get(), GregicTinkeringToolParts.SAW_HEAD_CAST, 2, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.WIRE_CUTTER_HEAD.get(), GregicTinkeringToolParts.WIRE_CUTTER_HEAD_CAST, 3, partFolder, castFolder);
		partCasting(consumer, GregicTinkeringToolParts.CROWBAR_HEAD.get(), GregicTinkeringToolParts.CROWBAR_HEAD_CAST, 2, partFolder, castFolder);

		castCreation(consumer, Ingredient.of(GregicTinkeringItems.WRENCH_HEAD_SHAPE.get()), GregicTinkeringToolParts.WRENCH_HEAD_CAST, castFolder, "wrench_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.HAMMER_HEAD_SHAPE.get()), GregicTinkeringToolParts.HAMMER_HEAD_CAST, castFolder, "hammer_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.FILE_HEAD_SHAPE.get()), GregicTinkeringToolParts.FILE_HEAD_CAST, castFolder, "file_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.SCREWDRIVER_HEAD_SHAPE.get()), GregicTinkeringToolParts.SCREWDRIVER_HEAD_CAST, castFolder, "screwdriver_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.SAW_HEAD_SHAPE.get()), GregicTinkeringToolParts.SAW_HEAD_CAST, castFolder, "saw_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.WIRE_CUTTER_HEAD_SHAPE.get()), GregicTinkeringToolParts.WIRE_CUTTER_HEAD_CAST, castFolder, "wire_cutter_head_from_shape");
		castCreation(consumer, Ingredient.of(GregicTinkeringItems.CROWBAR_HEAD_SHAPE.get()), GregicTinkeringToolParts.CROWBAR_HEAD_CAST, castFolder, "crowbar_head_from_shape");

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

		PartRecipeBuilder.partRecipe(part)
		                 .setPattern(id)
		                 .setCost(cost)
		                 .save(consumer, location(partFolder + "builder/" + id.getPath()));
	}
}
