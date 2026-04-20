package com.sward.gregictinkering.modules;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.tools.definition.module.aoe.AreaOfEffectIterator;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public record SneakConditionalAOEIterator(AreaOfEffectIterator.Loadable ifTrue, AreaOfEffectIterator.Loadable ifFalse) implements AreaOfEffectIterator.Loadable
{
	public static final RecordLoadable<SneakConditionalAOEIterator> LOADER = RecordLoadable.create(
		AreaOfEffectIterator.LOADER.defaultField(
			"if_true",
			AreaOfEffectIterator.EMPTY,
			SneakConditionalAOEIterator::ifTrue
		),
		AreaOfEffectIterator.LOADER.requiredField("if_false", SneakConditionalAOEIterator::ifFalse),
		SneakConditionalAOEIterator::new
	);

	@Override
	public @NotNull RecordLoadable<SneakConditionalAOEIterator> getLoader()
	{
		return LOADER;
	}

	@Override
	public @NotNull Iterable<BlockPos> getBlocks(
		@NotNull IToolStackView tool,
		@NotNull UseOnContext context,
		@NotNull BlockState state,
		@NotNull AOEMatchType matchType
	)
	{
		AreaOfEffectIterator iterator = context.getPlayer() != null && context.getPlayer().isCrouching() ? ifTrue : ifFalse;

		return iterator.getBlocks(tool, context, state, matchType);
	}
}
