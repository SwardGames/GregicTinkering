package com.sward.gregictinkering.client.model;

import com.sward.gregictinkering.GregicTinkeringMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings("deprecation")
public class GregicTinkeringItemProperties
{
	private static final ResourceLocation MINING_ID = GregicTinkeringMod.id("mining");

//	private static final ItemPropertyFunction MINING = (stack, level, entity, seed) ->
//		entity instanceof LocalPlayer plr &&
//		stack == entity.getMainHandItem() &&
//		Minecraft.getInstance().gameMode != null &&
//		Minecraft.getInstance().gameMode.isDestroying()
//			? 1
//			: 0;

	private static final ItemPropertyFunction MINING = (stack, level, entity, seed) ->
		entity instanceof LocalPlayer plr &&
		stack == entity.getMainHandItem() &&
		Minecraft.getInstance().options.keyAttack.isDown()
			? 1
			: 0;

	public static void registerMiningProperty(ItemLike itemLike)
	{
		ItemProperties.register(itemLike.asItem(), MINING_ID, MINING);
	}
}
