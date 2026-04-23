package com.sward.gregictinkering.recipe;

import com.google.common.collect.ImmutableList;
import com.sward.gregictinkering.GregicTinkeringRecipes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.common.IngredientLoadable;
import slimeknights.mantle.data.loadable.common.ItemStackLoadable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.IMultiRecipe;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.recipe.partbuilder.*;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import javax.annotation.Nullable;
import java.util.List;

@RequiredArgsConstructor
public class PartBuilderRecipe implements IPartBuilderRecipe, IMultiRecipe<IDisplayPartBuilderRecipe>
{
	public static final RecordLoadable<PartBuilderRecipe> LOADER = RecordLoadable.create(
		ContextKey.ID.requiredField(),
		LoadableRecipeSerializer.RECIPE_GROUP,
		Pattern.PARSER.requiredField("pattern", PartBuilderRecipe::getPattern),
		IngredientLoadable.DISALLOW_EMPTY.defaultField("pattern_item", DEFAULT_PATTERNS, PartBuilderRecipe::getPatternItem),
		IngredientLoadable.DISALLOW_EMPTY.requiredField("input", PartBuilderRecipe::getInput),
		IntLoadable.FROM_ONE.requiredField("cost", PartBuilderRecipe::getCost),
		ItemStackLoadable.REQUIRED_STACK_NBT.requiredField("result", PartBuilderRecipe::getResult),
		PartBuilderRecipe::new
	);

	@Getter
	protected final ResourceLocation id;

	@Getter
	protected final String group;

	@Getter
	protected final Pattern pattern;

	@Getter
	protected final Ingredient patternItem;

	@Getter
	protected final Ingredient input;

	@Getter
	protected final int cost;

	@Getter
	protected final ItemStack result;

	@Nullable
	private List<IDisplayPartBuilderRecipe> recipe;

	@Override
	public @NotNull RecipeSerializer<?> getSerializer()
	{
		return GregicTinkeringRecipes.PART_BUILDER_RECIPE_SERIALIZER.get();
	}

	@Override
	public boolean partialMatch(IPartBuilderContainer inv)
	{
		if (!patternItem.test(inv.getPatternStack()))
		{
			return false;
		}

		ItemStack stack = inv.getStack();

		if (!stack.isEmpty())
		{
			return input.test(stack);
		}

		return true;
	}

	@Override
	public boolean matches(IPartBuilderContainer inv, @NotNull Level world)
	{
		return input.test(inv.getStack()) && inv.getStack().getCount() >= cost;
	}

	@Override
	public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access)
	{
		return result;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull IPartBuilderContainer inv, @NotNull RegistryAccess access)
	{
		return result.copy();
	}

	@Override
	public @NotNull List<IDisplayPartBuilderRecipe> getRecipes(@NotNull RegistryAccess access) {
		if (recipe == null) {
			recipe = ImmutableList.of(
				new DisplayPartRecipe(
					id,
					MaterialVariant.of(MaterialIds.stone),
					pattern,
					List.of(patternItem.getItems()),
					cost,
					List.of(input.getItems()),
					List.of(result))
			);
		}
		return recipe;
	}
}
