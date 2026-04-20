package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ToolElectricItemCapability;
import com.sward.gregictinkering.materials.stats.*;
import com.sward.gregictinkering.modules.FuelTypeNameModule;
import com.sward.gregictinkering.modules.SneakConditionalAOEIterator;
import com.sward.gregictinkering.tools.stat.EnumToolStat;
import com.sward.gregictinkering.tools.stat.IntToolStat;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.definition.module.ToolModule;
import slimeknights.tconstruct.library.tools.definition.module.aoe.AreaOfEffectIterator;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public final class GregicTinkeringToolStats
{
	/** Internal material stats ID for the sake of adding traits exclusive to power tools */
	public static final MaterialStatsId POWER_TOOL = new MaterialStatsId(GregicTinkeringMod.id("power_tool"));

	/** Probability that damage is absorbed by power */
	public static final FloatToolStat POWER_REINFORCEMENT = new FloatToolStat(id("power_reinforcement"), 0xFF8547CC, 0F, 0F, 1F, GregicTinkeringTags.Items.POWER_TOOL);

	/** Multiplier on how much EU is produced by burning fuel */
	public static final FloatToolStat EFFICIENCY = new FloatToolStat(id("efficiency"), 0xFF8547CC, 1F, 0F, 2F, GregicTinkeringTags.Items.POWER_TOOL);

	/** How much power is consumed to perform an action (dig, attack, etc.) */
	public static final IntToolStat POWER_DRAW = new IntToolStat(id("power_draw"), 0xFFEE6666, 0, 0, Integer.MAX_VALUE, GregicTinkeringTags.Items.POWER_TOOL);

	/** Which type of fuel is consumed to produce power. */
	public static final EnumToolStat<FuelType> FUEL_TYPE = new EnumToolStat<>(FuelType.class, id("fuel_type"), FuelType.EU, GregicTinkeringTags.Items.POWER_TOOL);

	private static ToolStatId id(String name)
	{
		return new ToolStatId(GregicTinkeringMod.MOD_ID, name);
	}

	@SubscribeEvent
	void register(RegisterEvent event)
	{
		if (event.getRegistryKey() == Registries.ITEM)
		{
			MaterialRegistry.getInstance().registerStatType(SoftMalletHeadMaterialStats.TYPE, MaterialRegistry.MELEE_HARVEST);
			MaterialRegistry.getInstance().registerStatType(PlungerHeadMaterialStats.TYPE, MaterialRegistry.MELEE_HARVEST);

			MaterialRegistry.getInstance().registerStatType(EngineMaterialStats.TYPE, POWER_TOOL);
			MaterialRegistry.getInstance().registerStatType(BatteryMaterialStats.TYPE, POWER_TOOL);
			MaterialRegistry.getInstance().registerStatType(GregicStatlessMaterialStats.CASING.getType(), POWER_TOOL);
		}
		else if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER)
		{
			ToolStats.register(POWER_REINFORCEMENT);

			ToolStats.register(EFFICIENCY);
			ToolStats.register(POWER_DRAW);
			ToolStats.register(FUEL_TYPE);

			ToolStats.register(ToolElectricItemCapability.MAX_CHARGE_STAT);
			ToolStats.register(ToolElectricItemCapability.POWER_TIER_STAT);

			AreaOfEffectIterator.register(GregicTinkeringMod.id("sneak_conditional_aoe"), SneakConditionalAOEIterator.LOADER);
			ToolModule.LOADER.register(GregicTinkeringMod.id("fuel_type_name"), FuelTypeNameModule.LOADER);
		}
	}
}
