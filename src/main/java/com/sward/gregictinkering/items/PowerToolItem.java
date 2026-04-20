package com.sward.gregictinkering.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public class PowerToolItem extends ModifiableItem
{
	public PowerToolItem(
		Properties properties,
		ToolDefinition toolDefinition
	)
	{
		super(properties, toolDefinition);
	}

	@Override
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
	{
		return true;
	}
}
