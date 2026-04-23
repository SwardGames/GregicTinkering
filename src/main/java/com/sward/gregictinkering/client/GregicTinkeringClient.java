package com.sward.gregictinkering.client;

import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringTools;
import com.sward.gregictinkering.client.model.GregicTinkeringItemProperties;
import net.minecraft.world.item.Item;
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
				registerToolItemProperties(GregicTinkeringTools.WRENCH.get());
				registerToolItemProperties(GregicTinkeringTools.HAMMER.get());
				registerToolItemProperties(GregicTinkeringTools.FILE.get());
				registerToolItemProperties(GregicTinkeringTools.SCREWDRIVER.get());
				registerToolItemProperties(GregicTinkeringTools.SAW.get());
				registerToolItemProperties(GregicTinkeringTools.WIRE_CUTTER.get());
				registerToolItemProperties(GregicTinkeringTools.CROWBAR.get());
				registerToolItemProperties(GregicTinkeringTools.SOFT_MALLET.get());
				registerToolItemProperties(GregicTinkeringTools.PLUNGER.get());

				registerElectricToolItemProperties(GregicTinkeringTools.DRILL.get());
				registerElectricToolItemProperties(GregicTinkeringTools.CHAINSAW.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_WRENCH.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_HAMMER.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_FILE.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_SCREWDRIVER.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_SAW.get());
				registerElectricToolItemProperties(GregicTinkeringTools.POWERED_WIRE_CUTTER.get());
			}
		);
	}

	private static void registerToolItemProperties(Item item)
	{
		TinkerItemProperties.registerToolProperties(item);
	}

	private static void registerElectricToolItemProperties(Item item)
	{
		registerToolItemProperties(item);
		GregicTinkeringItemProperties.registerMiningProperty(item);
	}
}
