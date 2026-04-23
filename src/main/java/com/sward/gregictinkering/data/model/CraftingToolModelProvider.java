package com.sward.gregictinkering.data.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CraftingToolModelProvider implements DataProvider
{
	private static final String[] PARTS = {"head", "handle", "binding"};
	private final PackOutput.PathProvider pathProvider;
	private final ExistingFileHelper existingFileHelper;
	private final ResourceLocation[] tools;

	public CraftingToolModelProvider(
		PackOutput packOutput,
		ExistingFileHelper existingFileHelper,
		ResourceLocation... tools
	)
	{
		this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
		this.existingFileHelper = existingFileHelper;
		this.tools = tools;
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output)
	{
		List<CompletableFuture<?>> futures = new ArrayList<>();

		for (ResourceLocation tool : tools)
		{
			if (!existingFileHelper.exists(tool, PackType.CLIENT_RESOURCES, ".json", "models/item"))
			{
				futures.add(
					DataProvider.saveStable(
						output,
						buildJson(tool, "", true),
						pathProvider.json(tool)
					)
				);
			}

			if (!existingFileHelper.exists(
				tool.withSuffix("/broken"),
				PackType.CLIENT_RESOURCES,
				".json",
				"models/item"
			))
			{
				futures.add(
					DataProvider.saveStable(
						output,
						buildJson(tool, "_broken", false),
						pathProvider.json(tool.withSuffix("/broken"))
					)
				);
			}
		}

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	private JsonObject buildJson(ResourceLocation toolId, String headSuffix, boolean hasOverrides)
	{
		String toolName = toolId.getPath();

		JsonObject root = new JsonObject();
		root.addProperty("loader", "tconstruct:tool");
		root.addProperty("parent", "tconstruct:item/base/axe");

		JsonObject textures = new JsonObject();
		textures.add("head", new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/head" + headSuffix));
		textures.add("handle", new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/handle"));
		textures.add("binding", new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/binding"));
		root.add("textures", textures);

		JsonArray modifierRoots = new JsonArray();
		modifierRoots.add(new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/modifiers/"));
		root.add("modifier_roots", modifierRoots);

		JsonArray parts = new JsonArray();
		for (int i = 0; i < PARTS.length; ++i)
		{
			JsonObject part = new JsonObject();
			part.add("name", new JsonPrimitive(PARTS[i]));
			part.add("index", new JsonPrimitive(i));
			parts.add(part);
		}
		root.add("parts", parts);

		if (hasOverrides)
		{
			JsonArray overrides = new JsonArray();
			JsonObject broken = new JsonObject();
			JsonObject broken_predicate = new JsonObject();
			broken_predicate.add("tconstruct:broken", new JsonPrimitive(1));
			broken.add("predicate", broken_predicate);
			broken.add("model", new JsonPrimitive("gregic_tinkering:item/" + toolName + "/broken"));
			overrides.add(broken);
			root.add("overrides", overrides);
		}

		return root;
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Crafting Tool Models";
	}
}
