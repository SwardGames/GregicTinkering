package com.sward.gregictinkering.data.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sward.gregictinkering.GregicTinkeringMod;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class ShapedToolPartRecipeProvider implements DataProvider
{
	private final PackOutput.PathProvider pathProvider;
	private final RegistryObject<ToolPartItem> toolPart;
	private final Collection<MaterialId> materials;

	public ShapedToolPartRecipeProvider(PackOutput packOutput, RegistryObject<ToolPartItem> toolPart, Collection<MaterialId> materials, String folder)
	{
		this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes/" + folder);
		this.toolPart = toolPart;
		this.materials = materials;
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output)
	{
		List<CompletableFuture<?>> futures = new ArrayList<>();

		for (MaterialId material : materials)
		{
			futures.add(
				DataProvider.saveStable(
					output,
					buildRecipe(material),
					pathProvider.json(GregicTinkeringMod.id(material.getPath()))
				)
			);
		}

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	private JsonObject buildRecipe(MaterialId materialId)
	{
		String fullId = materialId.toString();

		JsonObject root = new JsonObject();
		root.addProperty("type", "minecraft:crafting_shaped");
		root.addProperty("category", "misc");

		JsonArray pattern = new JsonArray();
		buildPattern(pattern);
		root.add("pattern", pattern);

		JsonObject key = new JsonObject();
		buildKey(key, materialId);
		root.add("key", key);

		JsonObject result = new JsonObject();
		result.addProperty("item", toolPart.getId().toString());
		result.addProperty("count", 1);
		JsonObject nbt = new JsonObject();
		nbt.addProperty("Material", fullId);
		result.add("nbt", nbt);
		root.add("result", result);

		root.addProperty("show_notification", false);

		return root;
	}

	protected abstract void buildPattern(JsonArray pattern);

	protected abstract void buildKey(JsonObject key, MaterialId materialId);

	protected static JsonObject tagIngredient(String tag)
	{
		JsonObject result = new JsonObject();
		result.addProperty("tag", tag);
		return result;
	}
}
