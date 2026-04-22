package com.sward.gregictinkering.data.tags;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringToolParts;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.registration.CastItemObject;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModItemTagsProvider extends ItemTagsProvider
{
	public ModItemTagsProvider(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> lookupProvider,
		CompletableFuture<TagLookup<Block>> blockTagProvider,
		ExistingFileHelper existingFileHelper
	)
	{
		super(output, lookupProvider, blockTagProvider, GregicTinkeringMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider provider)
	{
		IntrinsicTagAppender<Item> goldCasts = this.tag(TinkerTags.Items.GOLD_CASTS);
		IntrinsicTagAppender<Item> sandCasts = this.tag(TinkerTags.Items.SAND_CASTS);
		IntrinsicTagAppender<Item> redSandCasts = this.tag(TinkerTags.Items.RED_SAND_CASTS);
		IntrinsicTagAppender<Item> singleUseCasts = this.tag(TinkerTags.Items.SINGLE_USE_CASTS);
		IntrinsicTagAppender<Item> multiUseCasts = this.tag(TinkerTags.Items.MULTI_USE_CASTS);

		Consumer<CastItemObject> addCast = cast -> {
			// tag based on material
			goldCasts.add(cast.get());
			sandCasts.add(cast.getSand());
			redSandCasts.add(cast.getRedSand());
			// tag based on usage
			singleUseCasts.addTag(cast.getSingleUseTag());
			this.tag(cast.getSingleUseTag()).add(cast.getSand(), cast.getRedSand());
			multiUseCasts.addTag(cast.getMultiUseTag());
			this.tag(cast.getMultiUseTag()).add(cast.get());
		};

		addCast.accept(GregicTinkeringToolParts.WRENCH_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.HAMMER_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.FILE_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.SCREWDRIVER_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.SAW_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.WIRE_CUTTER_HEAD_CAST);
		addCast.accept(GregicTinkeringToolParts.CROWBAR_HEAD_CAST);
	}
}
