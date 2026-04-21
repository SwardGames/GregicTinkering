package com.sward.gregictinkering;

import com.sward.gregictinkering.materials.stats.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import static com.sward.gregictinkering.GregicTinkeringMod.*;

public class GregicTinkeringToolParts
{
	private static final Item.Properties PROPS = new Item.Properties();

	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	/** Simple Tool Heads */
	public static final RegistryObject<ToolPartItem> WRENCH_HEAD = ITEMS.register("wrench_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> HAMMER_HEAD = ITEMS.register("hammer_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> FILE_HEAD = ITEMS.register("file_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> SCREWDRIVER_HEAD = ITEMS.register("screwdriver_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> SAW_HEAD = ITEMS.register("saw_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> WIRE_CUTTER_HEAD = ITEMS.register("wire_cutter_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> CROWBAR_HEAD = ITEMS.register("crowbar_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> SOFT_MALLET_HEAD = ITEMS.register("soft_mallet_head", () -> new ToolPartItem(PROPS, SoftMalletHeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> PLUNGER_HEAD = ITEMS.register("plunger_head", () -> new ToolPartItem(PROPS, PlungerHeadMaterialStats.ID));

	/** Power tool heads */
	public static final RegistryObject<ToolPartItem> DRILL_HEAD = ITEMS.register("drill_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> CHAINSAW_HEAD = ITEMS.register("chainsaw_head", () -> new ToolPartItem(PROPS, HeadMaterialStats.ID));

	/** Other power tool parts */
	public static final RegistryObject<ToolPartItem> CASING = ITEMS.register("casing", () -> new ToolPartItem(PROPS, GregicStatlessMaterialStats.CASING.getIdentifier()));
	public static final RegistryObject<ToolPartItem> ENGINE = ITEMS.register("engine", () -> new ToolPartItem(PROPS, EngineMaterialStats.ID));
	public static final RegistryObject<ToolPartItem> BATTERY = ITEMS.register("battery", () -> new ToolPartItem(PROPS, BatteryMaterialStats.ID));

	/** Casts */
	public static final CastItemObject WRENCH_HEAD_CAST = registerCast("wrench_head");
	public static final CastItemObject HAMMER_HEAD_CAST = registerCast("hammer_head");
	public static final CastItemObject FILE_HEAD_CAST = registerCast("file_head");
	public static final CastItemObject SCREWDRIVER_HEAD_CAST = registerCast("screwdriver_head");
	public static final CastItemObject SAW_HEAD_CAST = registerCast("saw_head");
	public static final CastItemObject WIRE_CUTTER_HEAD_CAST = registerCast("wire_cutter_head");
	public static final CastItemObject CROWBAR_HEAD_CAST = registerCast("crowbar_head");

	private static CastItemObject registerCast(String name)
	{
		ItemObject<Item> cast = new ItemObject<>(ITEMS.register(name + "_cast", () -> new Item(PROPS)));
		ItemObject<Item> sandCast = new ItemObject<>(ITEMS.register(name + "_sand_cast", () -> new Item(PROPS)));
		ItemObject<Item> redSandCast = new ItemObject<>(ITEMS.register(name + "_red_sand_cast", () -> new Item(PROPS)));
		return new CastItemObject(id(name), cast, sandCast, redSandCast);
	}
}
