package com.sward.gregictinkering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.sward.gregictinkering.GregicTinkeringMod.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringItems
{
	private static final Item.Properties PROPS = new Item.Properties();

	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

	public static final RegistryObject<Item> WRENCH_HEAD_SHAPE = ITEMS.register("wrench_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> HAMMER_HEAD_SHAPE = ITEMS.register("hammer_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> FILE_HEAD_SHAPE = ITEMS.register("file_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> SCREWDRIVER_HEAD_SHAPE = ITEMS.register("screwdriver_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> SAW_HEAD_SHAPE = ITEMS.register("saw_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> WIRE_CUTTER_HEAD_SHAPE = ITEMS.register("wire_cutter_head_shape", () -> new Item(PROPS));
	public static final RegistryObject<Item> CROWBAR_HEAD_SHAPE = ITEMS.register("crowbar_head_shape", () -> new Item(PROPS));
}
