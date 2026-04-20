package com.sward.gregictinkering.client;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringTools;
import com.sward.gregictinkering.client.model.GregicTinkeringItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;

@Mod.EventBusSubscriber(modid = GregicTinkeringMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GregicTinkeringClient
{
	@SubscribeEvent
	static void clientSetupEvent(FMLClientSetupEvent event)
	{
		event.enqueueWork(
			() ->
			{
				TinkerItemProperties.registerToolProperties(GregicTinkeringTools.DRILL.get());
				GregicTinkeringItemProperties.registerMiningProperty(GregicTinkeringTools.DRILL.get());

				TinkerItemProperties.registerToolProperties(GregicTinkeringTools.CHAINSAW.get());
				GregicTinkeringItemProperties.registerMiningProperty(GregicTinkeringTools.CHAINSAW.get());
			}
		);
	}
}
