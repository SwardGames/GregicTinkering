package com.sward.gregictinkering.util;

import com.sward.gregictinkering.modifiers.PoweredModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class PowerToolHelper
{
	public static boolean hasPower(@NotNull IToolStackView tool, @Nullable LivingEntity holder)
	{
		if (!tool.getHook(PoweredModifier.HOOK).hasPower(tool, null, holder))
		{
			return false;
		}

		for (ModifierEntry entry : tool.getModifierList())
		{
			boolean hasPower = entry.getHook(PoweredModifier.HOOK).hasPower(tool, entry, holder);

			if (!hasPower)
			{
				return false;
			}
		}

		return true;
	}

	public static boolean canCraftWith(ItemStack stack)
	{
		if (ToolDamageUtil.isBroken(stack))
		{
			return false;
		}

		ToolStack tool = ToolStack.from(stack);

		if (!hasPower(tool, null))
		{
			return false;
		}

		return true;
	}
}
