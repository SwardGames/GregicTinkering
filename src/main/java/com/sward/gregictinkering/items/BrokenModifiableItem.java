package com.sward.gregictinkering.items;

import com.sward.gregictinkering.tools.IBrokenTool;
import lombok.Getter;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

import java.util.function.Supplier;

public class BrokenModifiableItem extends ModifiableItem implements IBrokenTool
{
	@Getter
	private final Supplier<Item> repairedItem;

	public BrokenModifiableItem(
		Properties properties,
		ToolDefinition toolDefinition,
		Supplier<Item> repairedItem
	)
	{
		super(properties, toolDefinition);
		this.repairedItem = repairedItem;
	}
}
