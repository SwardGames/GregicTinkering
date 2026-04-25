package com.sward.gregictinkering;

import lombok.Getter;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class GregicTinkeringConfig
{
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.IntValue AIR_TANK_RECHARGE_RATE = BUILDER
		.comment("How quickly air ticks from the Air Tank modifier recharges per tick.")
		.defineInRange("air_tank_recharge_rate", 10, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue AIR_CONSUMED_PER_ATTACK = BUILDER
		.comment("How many air ticks are consumed per melee attack.")
		.defineInRange("air_consumed_per_attack", 25, 0, Integer.MAX_VALUE);

	static final ForgeConfigSpec SPEC = BUILDER.build();

	@Getter
	private static int airTankRechargeRate;

	@Getter
	private static int airConsumedPerAttack;

	@SuppressWarnings("unused")
	static void onModConfig(final ModConfigEvent event)
	{
		airTankRechargeRate = AIR_TANK_RECHARGE_RATE.get();
		airConsumedPerAttack = AIR_CONSUMED_PER_ATTACK.get();
	}
}
