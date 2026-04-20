package com.sward.gregictinkering.data.model;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.data.model.MaterialModelBuilder;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.part.MaterialItem;

public class GregicItemModelProvider extends ItemModelProvider
{
	private final ModelFile.UncheckedModelFile GENERATED = new ModelFile.UncheckedModelFile("item/generated");

	public GregicItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
	{
		super(output, GregicTinkeringMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		// Tool Parts
		part(GregicTinkeringToolParts.WRENCH_HEAD, "wrench/head").offset(-4, 4);
		part(GregicTinkeringToolParts.HAMMER_HEAD, "hammer/head").offset(-4, 4);
		part(GregicTinkeringToolParts.FILE_HEAD, "file/head").offset(-4, 4);
		part(GregicTinkeringToolParts.SCREWDRIVER_HEAD, "screwdriver/head").offset(-4, 4);
		part(GregicTinkeringToolParts.SAW_HEAD, "saw/head").offset(-4, 4);
		part(GregicTinkeringToolParts.WIRE_CUTTER_HEAD, "wire_cutter/head").offset(-4, 4);
		part(GregicTinkeringToolParts.CROWBAR_HEAD, "crowbar/head").offset(-4, 4);
		part(GregicTinkeringToolParts.SOFT_MALLET_HEAD, "soft_mallet/head").offset(-4, 4);
		part(GregicTinkeringToolParts.PLUNGER_HEAD, "plunger/head").offset(-4, 4);

		part(GregicTinkeringToolParts.DRILL_HEAD, "drill/head").offset(4, 4);
		part(GregicTinkeringToolParts.CHAINSAW_HEAD, "chainsaw/head").offset(4, 4);

		part(GregicTinkeringToolParts.CASING, "drill/casing").offset(-2, -2);

		// Casts
		cast(GregicTinkeringToolParts.WRENCH_HEAD_CAST);
		cast(GregicTinkeringToolParts.HAMMER_HEAD_CAST);
		cast(GregicTinkeringToolParts.FILE_HEAD_CAST);
		cast(GregicTinkeringToolParts.SCREWDRIVER_HEAD_CAST);
		cast(GregicTinkeringToolParts.SAW_HEAD_CAST);
		cast(GregicTinkeringToolParts.WIRE_CUTTER_HEAD_CAST);
		cast(GregicTinkeringToolParts.CROWBAR_HEAD_CAST);
	}

	@SuppressWarnings("deprecation")
	private ResourceLocation id(ItemLike item)
	{
		return BuiltInRegistries.ITEM.getKey(item.asItem());
	}

	/**
	 * Generated item with a texture
	 */
	private ItemModelBuilder generated(ResourceLocation item, ResourceLocation texture)
	{
		return getBuilder(item.toString()).parent(GENERATED).texture("layer0", texture);
	}

	/**
	 * Generated item with a texture
	 */
	private ItemModelBuilder generated(ResourceLocation item, String texture)
	{
		return generated(item, ResourceLocation.fromNamespaceAndPath(item.getNamespace(), texture));
	}

	/**
	 * Generated item with a texture
	 */
	private ItemModelBuilder generated(ItemLike item, String texture)
	{
		return generated(id(item), texture);
	}

	/**
	 * Generated item with a texture
	 */
	private ItemModelBuilder basicItem(ResourceLocation item, String texture)
	{
		return generated(item, "item/" + texture);
	}

	/**
	 * Generated item with a texture
	 */
	private ItemModelBuilder basicItem(ItemLike item, String texture)
	{
		return basicItem(id(item), texture);
	}


	/* Parts */

	/**
	 * Creates a part model with the given texture
	 */
	private MaterialModelBuilder<ItemModelBuilder> part(ResourceLocation part, String texture)
	{
		return withExistingParent(part.getPath(), "forge:item/default")
			.texture("texture", GregicTinkeringMod.id("item/tool/" + texture))
			.customLoader(MaterialModelBuilder::new);
	}

	/**
	 * Creates a part model in the parts folder
	 */
	private MaterialModelBuilder<ItemModelBuilder> part(Item item, String texture)
	{
		return part(id(item), texture);
	}

	/**
	 * Creates a part model with the given texture
	 */
	private MaterialModelBuilder<ItemModelBuilder> part(RegistryObject<? extends MaterialItem> part, String texture)
	{
		return part(part.getId(), texture);
	}

	/**
	 * Creates a part model in the parts folder
	 */
	private void part(RegistryObject<? extends MaterialItem> part)
	{
		part(part, "parts/" + part.getId().getPath());
	}


	/**
	 * Creates models for the given cast object
	 */
	private void cast(CastItemObject cast)
	{
		String name = cast.getName().getPath();
		basicItem(cast.getId(), "cast/" + name);
		basicItem(cast.getSand(), "sand_cast/" + name);
		basicItem(cast.getRedSand(), "red_sand_cast/" + name);
	}
}
