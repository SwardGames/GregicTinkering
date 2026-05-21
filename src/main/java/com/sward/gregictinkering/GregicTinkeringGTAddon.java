package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.sward.gregictinkering.data.recipe.ItemStackResultShapedRecipeBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.registration.CastItemObject;

import java.util.Set;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.gem;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.ingot;
import static com.sward.gregictinkering.GregicTinkeringToolParts.*;
import static com.gregtechceu.gtceu.api.GTValues.MV;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.EXTRUDER_RECIPES;

@GTAddon
public class GregicTinkeringGTAddon implements IGTAddon
{
	@Override
	public GTRegistrate getRegistrate()
	{
		return GregicTinkeringMod.REGISTRATE;
	}

	@Override
	public void initializeAddon()
	{

	}

	@Override
	public String addonModId()
	{
		return GregicTinkeringMod.MOD_ID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> provider)
	{
		if (!ModList.get().isLoaded("gm_construct"))
		{
			return;
		}

		GregicTinkeringMod.LOGGER.info("Greg's Modern Construct found - generating tool part recipes.");

		Set<Material> invalidToolMaterials = Set.of(
			GTMaterials.Bronze,
			GTMaterials.Cobalt,
			GTMaterials.Copper,
			GTMaterials.Diamond,
			GTMaterials.Flint,
			GTMaterials.Invar,
			GTMaterials.Iron,
			GTMaterials.Netherite,
			GTMaterials.RoseGold,
			GTMaterials.Steel
		);

		TagKey<Item> hammerTag = TagKey.create(
			Registries.ITEM,
			ResourceLocation.fromNamespaceAndPath("forge", "tools/hammers")
		);

		Ingredient h = Ingredient.of(hammerTag);
		Ingredient w = Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "tools/wrench")));

		InventoryChangeTrigger.TriggerInstance unlockTrigger = new InventoryChangeTrigger.TriggerInstance(
			ContextAwarePredicate.ANY,
			MinMaxBounds.Ints.ANY,
			MinMaxBounds.Ints.ANY,
			MinMaxBounds.Ints.ANY,
			new ItemPredicate[]{ItemPredicate.Builder.item().of(hammerTag).build()}
		);

		for (Material material : GTCEuAPI.materialManager.getRegisteredMaterials())
		{
			if (invalidToolMaterials.contains(material))
			{
				continue;
			}

			if (!material.hasProperty(PropertyKey.TOOL))
			{
				continue;
			}

			if (material.getProperty(PropertyKey.TOOL).hasType(GTToolType.PICKAXE))
			{
				String name = material.getName();

				Ingredient p = Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "plates/" + name)));
				Ingredient g = Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "small_gears/" + name)));
				Ingredient r = Ingredient.of(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", "rings/" + name)));

				new ItemStackResultShapedRecipeBuilder(RecipeCategory.MISC, partStack(DRILL_HEAD.get(), material))
					.pattern("PPW")
					.pattern("PPP")
					.pattern("HPG")
					.define('P', p)
					.define('G', g)
					.define('W', w)
					.define('H', h)
					.showNotification(false)
					.unlockedBy("has_hammer", unlockTrigger)
					.save(provider, GregicTinkeringMod.id("drill_heads/" + name));

				new ItemStackResultShapedRecipeBuilder(RecipeCategory.MISC, partStack(CHAINSAW_HEAD.get(), material))
					.pattern("RRW")
					.pattern("RPR")
					.pattern("HRG")
					.define('P', p)
					.define('R', r)
					.define('G', g)
					.define('W', w)
					.define('H', h)
					.showNotification(false)
					.unlockedBy("has_hammer", unlockTrigger)
					.save(provider, GregicTinkeringMod.id("chainsaw_heads/" + name));

				MaterialEntry materialEntry = new MaterialEntry(material.hasProperty(PropertyKey.GEM) ? gem : ingot, material);

				generateExtruderRecipes(materialEntry, WRENCH_HEAD, 4, WRENCH_HEAD_CAST, "wrench_head", provider);
				generateExtruderRecipes(materialEntry, HAMMER_HEAD, 6, HAMMER_HEAD_CAST, "hammer_head", provider);
				generateExtruderRecipes(materialEntry, FILE_HEAD, 2, FILE_HEAD_CAST, "file_head", provider);
				generateExtruderRecipes(materialEntry, SCREWDRIVER_HEAD, 2, SCREWDRIVER_HEAD_CAST, "screwdriver_head", provider);
				generateExtruderRecipes(materialEntry, SAW_HEAD, 2, SAW_HEAD_CAST, "saw_head", provider);
				generateExtruderRecipes(materialEntry, WIRE_CUTTER_HEAD, 3, WIRE_CUTTER_HEAD_CAST, "wire_cutter_head", provider);
				generateExtruderRecipes(materialEntry, CROWBAR_HEAD, 2, CROWBAR_HEAD_CAST, "crowbar_head", provider);
			}
		}
	}

	private static void generateExtruderRecipes(
		@NotNull MaterialEntry entry,
		@NotNull RegistryObject<? extends Item> toolPartItem,
		int materialCost,
		@NotNull CastItemObject cast,
		@NotNull String path,
		@NotNull Consumer<FinishedRecipe> provider
	)
	{
		EXTRUDER_RECIPES.recipeBuilder(GregicTinkeringMod.id("extrude_" + entry.material().getName() + "_to_" + path))
		                .inputItems(entry, materialCost)
		                .notConsumable(cast)
		                .outputItems(partStack(toolPartItem.get(), entry.material()))
		                .duration((int) (20 * entry.material().getMass() * materialCost))
		                .EUt(VA[MV])
		                .save(provider);
	}

	private static @NotNull ItemStack partStack(@NotNull Item toolPartItem, @NotNull Material material)
	{
		ItemStack result = new ItemStack(toolPartItem);
		result.getOrCreateTag().putString("Material", "gm_construct:" + material.getName());
		return result;
	}
}
