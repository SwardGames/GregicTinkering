package com.sward.gregictinkering.data.sprite;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnimatedMaterialPartMetaProvider implements DataProvider
{
	private final ExistingFileHelper existingFileHelper;
	private final PackOutput.PathProvider pathProvider;
	/**
	 * Sprite provider
	 */
	private final AbstractPartSpriteProvider partProvider;
	/**
	 * Materials to provide
	 */
	private final AbstractMaterialSpriteProvider[] materialProviders;
	private final GeneratorPartTextureJsonGenerator.StatOverride overrides;

	public AnimatedMaterialPartMetaProvider(
		PackOutput packOutput,
		ExistingFileHelper existingFileHelper,
		AbstractPartSpriteProvider spriteProvider,
		GeneratorPartTextureJsonGenerator.StatOverride overrides,
		AbstractMaterialSpriteProvider... materialProviders
	)
	{
		this.existingFileHelper = existingFileHelper;
		this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures");
		this.partProvider = spriteProvider;
		this.overrides = overrides;
		this.materialProviders = materialProviders;
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output)
	{
		List<CompletableFuture<?>> tasks = new ArrayList<>();

		List<AbstractPartSpriteProvider.PartSpriteInfo> parts = partProvider.getSprites();

		if (parts.isEmpty())
		{
			throw new IllegalStateException(partProvider.getName() + " has no parts, must have at least one part to generate");
		}

		for (AbstractMaterialSpriteProvider materialProvider : materialProviders)
		{
			Collection<AbstractMaterialSpriteProvider.MaterialSpriteInfo> materials = materialProvider.getMaterials().values();

			if (materials.isEmpty())
			{
				throw new IllegalStateException(materialProvider.getName() + " has no materials, must have at least one material to generate");
			}

			for (AbstractPartSpriteProvider.PartSpriteInfo part : parts)
			{
				if (!existingFileHelper.exists(part.getPath(), PackType.CLIENT_RESOURCES, ".png.mcmeta", "textures"))
				{
					continue;
				}

				try
				{
					JsonElement metaJson = JsonParser.parseReader(
						existingFileHelper.getResource(
							part.getPath(),
							PackType.CLIENT_RESOURCES,
							".png.mcmeta",
							"textures"
						).openAsReader()
					);

					for (AbstractMaterialSpriteProvider.MaterialSpriteInfo material : materials)
					{
						// if the part skips variants and the material is a variant, skip
						if (!material.isVariant() || !part.isSkipVariants())
						{
							// if any stat type matches, generate it
							for (MaterialStatsId statType : part.getStatTypes())
							{
								if (material.supportStatType(statType) || overrides.hasOverride(
									statType,
									material.getTexture()
								))
								{
									tasks.add(
										DataProvider.saveStable(
											output,
											metaJson,
											this.pathProvider.file(MaterialPartTextureGenerator.outputPath(part, material), "png.mcmeta")
										)
									);

									break;
								}
							}
						}
					}
				}
				catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			}
		}

		return CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new))
								.thenRunAsync(partProvider::cleanCache);
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Part Head Sprite Animation Provider";
	}
}
