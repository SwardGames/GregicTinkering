package com.sward.gregictinkering.modifierhooks;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Collection;

public interface HasPowerModifierHook
{
	boolean hasPower(@NotNull IToolStackView tool, @Nullable ModifierEntry modifier, @Nullable LivingEntity holder);

	record Merger(Collection<HasPowerModifierHook> modules) implements HasPowerModifierHook
	{
		@Override
		public boolean hasPower(@NotNull IToolStackView tool, @Nullable ModifierEntry modifier, @Nullable LivingEntity holder)
		{
			for (HasPowerModifierHook hook : modules)
			{
				if (!hook.hasPower(tool, modifier, holder))
				{
					return false;
				}
			}

			return true;
		}
	}
}
