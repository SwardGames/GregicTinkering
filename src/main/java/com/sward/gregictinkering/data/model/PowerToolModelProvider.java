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

public class PowerToolModelProvider implements DataProvider
{
	private static final String[] PARTS = {"head", "casing", "engine", "battery"};
	private final PackOutput.PathProvider pathProvider;
	private final ExistingFileHelper existingFileHelper;
	private final ResourceLocation[] tools;

	public PowerToolModelProvider(
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
						buildJson(tool, "", true, null),
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
						buildJson(tool, "_broken", false, null),
						pathProvider.json(tool.withSuffix("/broken"))
					)
				);
			}

			if (!existingFileHelper.exists(
				tool.withSuffix("/active"),
				PackType.CLIENT_RESOURCES,
				".json",
				"models/item"
			))
			{
				futures.add(
					DataProvider.saveStable(
						output,
						buildJson(tool, "_active", false, "_active"),
						pathProvider.json(tool.withSuffix("/active"))
					)
				);
			}
		}

		return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
	}

	private JsonObject buildJson(
		ResourceLocation toolId,
		String headSuffix,
		boolean hasOverrides,
		String additionalModifierRootSuffix
	)
	{
		String toolName = toolId.getPath();

		JsonObject root = new JsonObject();
		root.addProperty("loader", "tconstruct:tool");
		root.addProperty("parent", "gregic_tinkering:item/base/power_tool");

		JsonObject textures = new JsonObject();
		textures.add("head", new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/head" + headSuffix));
		textures.add("casing", new JsonPrimitive("gregic_tinkering:item/tool/power_tool/casing"));
		textures.add("engine", new JsonPrimitive("gregic_tinkering:item/tool/power_tool/engine"));
		textures.add("battery", new JsonPrimitive("gregic_tinkering:item/tool/power_tool/battery"));
		textures.add("handle", new JsonPrimitive("gregic_tinkering:item/tool/power_tool/handle"));
		root.add("textures", textures);

		JsonArray modifierRoots = new JsonArray();
		if (additionalModifierRootSuffix != null)
		{
			modifierRoots.add(new JsonPrimitive("gregic_tinkering:item/tool/" + toolName + "/modifiers" + additionalModifierRootSuffix + "/"));
		}
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
		JsonObject part = new JsonObject();
		part.add("name", new JsonPrimitive("handle"));
		parts.add(part);
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

			JsonObject active = new JsonObject();
			JsonObject active_predicate = new JsonObject();
			active_predicate.add("gregic_tinkering:mining", new JsonPrimitive(1));
			active.add("predicate", active_predicate);
			active.add("model", new JsonPrimitive("gregic_tinkering:item/" + toolName + "/active"));
			overrides.add(active);

			root.add("overrides", overrides);
		}

		return root;
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Power Tool Models";
	}
}
