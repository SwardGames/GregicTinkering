package com.sward.gregictinkering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sward.gregictinkering.GregicTinkeringMod.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringItems
{
	private static final Item.Properties PROPS = new Item.Properties();

	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
}
