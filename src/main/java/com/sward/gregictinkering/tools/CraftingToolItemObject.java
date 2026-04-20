package com.sward.gregictinkering.tools;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.sward.gregictinkering.GregicTinkeringTools;
import com.sward.gregictinkering.items.BrokenModifiableItem;
import com.sward.gregictinkering.items.ModifiableGTToolItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

public class CraftingToolItemObject
{
	public final RegistryObject<ModifiableGTToolItem> tool;

	public final RegistryObject<BrokenModifiableItem> brokenTool;

	public CraftingToolItemObject(DeferredRegister<Item> items, String name, ToolDefinition toolDefinition, GTToolType toolType)
	{
		tool = items.register(name, () -> new ModifiableGTToolItem(GregicTinkeringTools.PROPS, toolDefinition, toolType, this::getBrokenToolItem));
		brokenTool = items.register(name, () -> new BrokenModifiableItem(GregicTinkeringTools.PROPS, toolDefinition, this::getToolItem));
	}

	public Item getToolItem()
	{
		return tool.get();
	}

	public Item getBrokenToolItem()
	{
		return brokenTool.get();
	}
}
