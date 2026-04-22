package com.sward.gregictinkering.data;

import com.sward.gregictinkering.GregicTinkeringTools;
import com.sward.gregictinkering.data.material.*;
import com.sward.gregictinkering.data.model.CraftingToolModelProvider;
import com.sward.gregictinkering.data.model.ToolPartItemModelProvider;
import com.sward.gregictinkering.data.recipe.CasingsRecipeProvider;
import com.sward.gregictinkering.data.recipe.ChainsawHeadsRecipeProvider;
import com.sward.gregictinkering.data.recipe.DrillHeadsRecipeProvider;
import com.sward.gregictinkering.data.recipe.ToolPartBuildingRecipeProvider;
import com.sward.gregictinkering.data.sprite.AnimatedMaterialPartMetaProvider;
import com.sward.gregictinkering.data.sprite.GregicPartSpriteProvider;
import com.sward.gregictinkering.data.tags.ModBlockTagsProvider;
import com.sward.gregictinkering.data.tags.ModItemTagsProvider;
import com.sward.gregictinkering.materials.stats.GregicStatlessMaterialStats;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.library.client.data.material.MaterialPartTextureGenerator;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.sward.gregictinkering.GregicTinkeringMod.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GregicTinkeringDataGen
{
	@SubscribeEvent
	public static void gatherData(final GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		boolean server = event.includeServer();

		RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
		DatapackBuiltinEntriesProvider datapackRegistryProvider = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, registrySetBuilder, Set.of(MOD_ID));
		generator.addProvider(server, datapackRegistryProvider);

		// Crafting Tool Heads
		CraftingToolMaterialDataProvider craftingToolMaterials = new CraftingToolMaterialDataProvider(packOutput);
		generator.addProvider(server, craftingToolMaterials);
		generator.addProvider(server, new CraftingToolMaterialStatsDataProvider(packOutput, craftingToolMaterials));
		generator.addProvider(server, new ToolPartBuildingRecipeProvider(packOutput));

		// Power Tool Heads (from TConstruct)
		Collection<MaterialId> metalHeadMaterials = Arrays.stream(TieredMaterialIds.HEAD_METALS).flatMap(Arrays::stream).toList();
		generator.addProvider(server, new DrillHeadsRecipeProvider(packOutput, metalHeadMaterials));
		generator.addProvider(server, new ChainsawHeadsRecipeProvider(packOutput, metalHeadMaterials));

		// Casing Materials (from TConstruct)
		CasingMaterialDataProvider casingMaterials = new CasingMaterialDataProvider(packOutput);
//		generator.addProvider(server, casingMaterials); // IMPORTANT: DO NOT REGISTER
		generator.addProvider(server, new CasingMaterialStatsDataProvider(packOutput, casingMaterials));
		generator.addProvider(server, new CasingsRecipeProvider(packOutput, Arrays.stream(CasingMaterialDataProvider.CASINGS).flatMap(Arrays::stream).toList()));

		// Power Tool Materials
		PowerToolMaterialDataProvider materials = new PowerToolMaterialDataProvider(packOutput);
		generator.addProvider(server, materials);
		generator.addProvider(server, new PowerToolMaterialStatsDataProvider(packOutput, materials));
		generator.addProvider(server, new PowerToolMaterialTraitsDataProvider(packOutput, materials));

		// Tags
		ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, MOD_ID, existingFileHelper);
		generator.addProvider(server, blockTagsProvider);
		generator.addProvider(server, new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

		boolean client = event.includeClient();

		TinkerMaterialSpriteProvider tcMaterialSprites = new TinkerMaterialSpriteProvider();
		GregicPartSpriteProvider partSprites = new GregicPartSpriteProvider();

		GeneratorPartTextureJsonGenerator.StatOverride.Builder overridesBuilder = new GeneratorPartTextureJsonGenerator.StatOverride.Builder();

		for (int i = 0; i < CasingMaterialDataProvider.CASINGS.length; ++i)
		{
			for (MaterialId metalId : CasingMaterialDataProvider.CASINGS[i])
			{
				overridesBuilder.add(GregicStatlessMaterialStats.CASING.getIdentifier(), metalId);
			}
		}

		GeneratorPartTextureJsonGenerator.StatOverride overrides = overridesBuilder.build();

		generator.addProvider(client, new ToolPartItemModelProvider(packOutput, existingFileHelper));
		generator.addProvider(
			client,
			new CraftingToolModelProvider(
				packOutput,
				existingFileHelper,
				GregicTinkeringTools.WRENCH.getId(),
				GregicTinkeringTools.HAMMER.getId(),
				GregicTinkeringTools.FILE.getId(),
				GregicTinkeringTools.SCREWDRIVER.getId(),
				GregicTinkeringTools.SAW.getId(),
				GregicTinkeringTools.WIRE_CUTTER.getId(),
				GregicTinkeringTools.CROWBAR.getId(),
				GregicTinkeringTools.SOFT_MALLET.getId(),
				GregicTinkeringTools.PLUNGER.getId()
			)
		);
		generator.addProvider(client, new GeneratorPartTextureJsonGenerator(packOutput, MOD_ID, partSprites, overrides));
		generator.addProvider(client, new MaterialPartTextureGenerator(packOutput, existingFileHelper, partSprites, overrides, tcMaterialSprites));
		generator.addProvider(client, new AnimatedMaterialPartMetaProvider(packOutput, existingFileHelper, partSprites, overrides, tcMaterialSprites));

		generator.addProvider(client, new GregicMaterialRenderInfoProvider(packOutput, new GregicMaterialSpriteProvider(), existingFileHelper));
	}
}
