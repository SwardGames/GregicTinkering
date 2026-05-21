package com.sward.gregictinkering.data.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.CraftingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class ItemStackResultShapedRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
	private final RecipeCategory category;
	private final ItemStack result;
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
	@Nullable
	private String group;
	private boolean showNotification = true;

	public ItemStackResultShapedRecipeBuilder(RecipeCategory category, ItemStack result) {
		this.category = category;
		this.result = result;
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public ItemStackResultShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
		return this.define(symbol, Ingredient.of(tag));
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public ItemStackResultShapedRecipeBuilder define(Character symbol, ItemLike item) {
		return this.define(symbol, Ingredient.of(item));
	}

	/**
	 * Adds a key to the recipe pattern.
	 */
	public ItemStackResultShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
		if (this.key.containsKey(symbol)) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
		} else if (symbol == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.key.put(symbol, ingredient);
			return this;
		}
	}

	/**
	 * Adds a new entry to the patterns for this recipe.
	 */
	public ItemStackResultShapedRecipeBuilder pattern(String pattern) {
		if (!this.rows.isEmpty() && pattern.length() != this.rows.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(pattern);
			return this;
		}
	}

	public @NotNull ItemStackResultShapedRecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull CriterionTriggerInstance criterionTrigger) {
		this.advancement.addCriterion(criterionName, criterionTrigger);
		return this;
	}

	public @NotNull ItemStackResultShapedRecipeBuilder group(@Nullable String groupName) {
		this.group = groupName;
		return this;
	}

	public ItemStackResultShapedRecipeBuilder showNotification(boolean showNotification) {
		this.showNotification = showNotification;
		return this;
	}

	public @NotNull Item getResult() {
		return this.result.getItem();
	}

	public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, @NotNull ResourceLocation recipeId) {
		this.ensureValid(recipeId);
		this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(
			AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
		finishedRecipeConsumer.accept(new ItemStackResultShapedRecipeBuilder.Result(recipeId, this.result, this.group == null ? "" : this.group, determineBookCategory(this.category), this.rows, this.key, this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification));
	}

	/**
	 * Makes sure that this recipe is valid and obtainable.
	 */
	private void ensureValid(ResourceLocation id) {
		if (this.rows.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
		} else {
			Set<Character> set = Sets.newHashSet(this.key.keySet());
			set.remove(' ');

			for(String s : this.rows) {
				for(int i = 0; i < s.length(); ++i) {
					char c0 = s.charAt(i);
					if (!this.key.containsKey(c0) && c0 != ' ') {
						throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
					}

					set.remove(c0);
				}
			}

			if (!set.isEmpty()) {
				throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
			} else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
				throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
			} else if (this.advancement.getCriteria().isEmpty()) {
				throw new IllegalStateException("No way of obtaining recipe " + id);
			}
		}
	}

	public static class Result extends CraftingRecipeBuilder.CraftingResult {
		@Getter
		private final ResourceLocation id;
		private final ItemStack result;
		private final String group;
		private final List<String> pattern;
		private final Map<Character, Ingredient> key;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;
		private final boolean showNotification;

		public Result(ResourceLocation id, ItemStack result, String group, CraftingBookCategory category, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation advancementId, boolean showNotification) {
			super(category);
			this.id = id;
			this.result = result;
			this.group = group;
			this.pattern = pattern;
			this.key = key;
			this.advancement = advancement;
			this.advancementId = advancementId;
			this.showNotification = showNotification;
		}

		public void serializeRecipeData(@NotNull JsonObject json) {
			super.serializeRecipeData(json);
			if (!this.group.isEmpty()) {
				json.addProperty("group", this.group);
			}

			JsonArray jsonarray = new JsonArray();

			for(String s : this.pattern) {
				jsonarray.add(s);
			}

			json.add("pattern", jsonarray);
			JsonObject jsonobject = new JsonObject();

			for(Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
				jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
			}

			json.add("key", jsonobject);

			JsonObject jsonobject1 = new JsonObject();
			jsonobject1.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result.getItem()).toString());
			jsonobject1.addProperty("count", this.result.getCount());
			CompoundTag nbt = this.result.getTag();
			if (nbt != null)
			{
				jsonobject1.add("nbt", JsonParser.parseString(nbt.toString()));
			}

			json.add("result", jsonobject1);
			json.addProperty("show_notification", this.showNotification);
		}

		public @NotNull RecipeSerializer<?> getType() {
			return RecipeSerializer.SHAPED_RECIPE;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}
