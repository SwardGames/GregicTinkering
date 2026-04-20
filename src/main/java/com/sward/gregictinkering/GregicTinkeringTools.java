package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.sward.gregictinkering.items.PowerToolItem;
import com.sward.gregictinkering.tools.CraftingToolItemObject;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

public final class GregicTinkeringTools
{
	public static final Item.Properties PROPS = (new Item.Properties()).stacksTo(1);

	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GregicTinkeringMod.MOD_ID);

	// Simple Tools
	public static final CraftingToolItemObject WRENCH = new CraftingToolItemObject(ITEMS, "wrench", GregicTinkeringToolDefinitions.WRENCH, GTToolType.WRENCH);
	public static final CraftingToolItemObject HAMMER = new CraftingToolItemObject(ITEMS, "hammer", GregicTinkeringToolDefinitions.HAMMER, GTToolType.HARD_HAMMER);
	public static final CraftingToolItemObject FILE = new CraftingToolItemObject(ITEMS, "file", GregicTinkeringToolDefinitions.FILE, GTToolType.FILE);
	public static final CraftingToolItemObject SCREWDRIVER = new CraftingToolItemObject(ITEMS, "screwdriver", GregicTinkeringToolDefinitions.SCREWDRIVER, GTToolType.SCREWDRIVER);
	public static final CraftingToolItemObject SAW = new CraftingToolItemObject(ITEMS, "saw", GregicTinkeringToolDefinitions.SAW, GTToolType.SAW);
	public static final CraftingToolItemObject WIRE_CUTTER = new CraftingToolItemObject(ITEMS, "wire_cutter", GregicTinkeringToolDefinitions.WIRE_CUTTER, GTToolType.WIRE_CUTTER);
	public static final CraftingToolItemObject CROWBAR = new CraftingToolItemObject(ITEMS, "crowbar", GregicTinkeringToolDefinitions.CROWBAR, GTToolType.CROWBAR);
	public static final CraftingToolItemObject SOFT_MALLET = new CraftingToolItemObject(ITEMS, "soft_mallet", GregicTinkeringToolDefinitions.SOFT_MALLET, GTToolType.SOFT_MALLET);
	public static final CraftingToolItemObject PLUNGER = new CraftingToolItemObject(ITEMS, "plunger", GregicTinkeringToolDefinitions.PLUNGER, GTToolType.PLUNGER);

	// Power Tools
	public static final RegistryObject<ModifiableItem> DRILL = ITEMS.register("drill", () -> new PowerToolItem(PROPS, GregicTinkeringToolDefinitions.DRILL));
	public static final RegistryObject<ModifiableItem> CHAINSAW = ITEMS.register("chainsaw", () -> new PowerToolItem(PROPS, GregicTinkeringToolDefinitions.CHAINSAW));
}
