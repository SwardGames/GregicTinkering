package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.sward.gregictinkering.tools.ModifiableGTToolItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringTools
{
	public static final Item.Properties PROPS = (new Item.Properties()).stacksTo(1);

	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GregicTinkeringMod.MOD_ID);

	// Simple Tools
	public static final RegistryObject<ModifiableGTToolItem> WRENCH = ITEMS.register("wrench", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.WRENCH, GTToolType.WRENCH));
	public static final RegistryObject<ModifiableGTToolItem> HAMMER = ITEMS.register("hammer", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.HAMMER, GTToolType.HARD_HAMMER));
	public static final RegistryObject<ModifiableGTToolItem> FILE = ITEMS.register("file", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.FILE, GTToolType.FILE));
	public static final RegistryObject<ModifiableGTToolItem> SCREWDRIVER = ITEMS.register("screwdriver", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.SCREWDRIVER, GTToolType.SCREWDRIVER));
	public static final RegistryObject<ModifiableGTToolItem> SAW = ITEMS.register("saw", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.SAW, GTToolType.SAW));
	public static final RegistryObject<ModifiableGTToolItem> WIRE_CUTTER = ITEMS.register("wire_cutter", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.WIRE_CUTTER, GTToolType.WIRE_CUTTER));
	public static final RegistryObject<ModifiableGTToolItem> CROWBAR = ITEMS.register("crowbar", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.CROWBAR, GTToolType.CROWBAR));
	public static final RegistryObject<ModifiableGTToolItem> SOFT_MALLET = ITEMS.register("soft_mallet", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.SOFT_MALLET, GTToolType.SOFT_MALLET));
	public static final RegistryObject<ModifiableGTToolItem> PLUNGER = ITEMS.register("plunger", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.PLUNGER, GTToolType.PLUNGER));

	// Power Tools
	public static final RegistryObject<ModifiableGTToolItem> DRILL = ITEMS.register("drill", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.DRILL, GTToolType.DRILL_LV, true));
	public static final RegistryObject<ModifiableGTToolItem> CHAINSAW = ITEMS.register("chainsaw", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.CHAINSAW, GTToolType.CHAINSAW_LV, true));

	public static final RegistryObject<ModifiableGTToolItem> POWERED_WRENCH = ITEMS.register("powered_wrench", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_WRENCH, GTToolType.WRENCH, true));
	public static final RegistryObject<ModifiableGTToolItem> POWERED_HAMMER = ITEMS.register("powered_hammer", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_HAMMER, GTToolType.HARD_HAMMER, true));
	public static final RegistryObject<ModifiableGTToolItem> POWERED_FILE = ITEMS.register("powered_file", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_FILE, GTToolType.FILE, true));
	public static final RegistryObject<ModifiableGTToolItem> POWERED_SCREWDRIVER = ITEMS.register("powered_screwdriver", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_SCREWDRIVER, GTToolType.SCREWDRIVER, true));
	public static final RegistryObject<ModifiableGTToolItem> POWERED_SAW = ITEMS.register("powered_saw", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_SAW, GTToolType.SAW, true));
	public static final RegistryObject<ModifiableGTToolItem> POWERED_WIRE_CUTTER = ITEMS.register("powered_wire_cutter", () -> new ModifiableGTToolItem(PROPS, GregicTinkeringToolDefinitions.POWERED_WIRE_CUTTER, GTToolType.WIRE_CUTTER, true));
}
