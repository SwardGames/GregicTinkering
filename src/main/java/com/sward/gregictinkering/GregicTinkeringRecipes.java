package com.sward.gregictinkering;

import com.sward.gregictinkering.recipe.PartBuilderRecipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringRecipes
{
	static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GregicTinkeringMod.MOD_ID);

	public static final RegistryObject<RecipeSerializer<PartBuilderRecipe>> PART_BUILDER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("part_builder", () -> LoadableRecipeSerializer.of(PartBuilderRecipe.LOADER));
}
