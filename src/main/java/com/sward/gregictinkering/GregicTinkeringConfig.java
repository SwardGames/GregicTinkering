package com.sward.gregictinkering;

import com.gregtechceu.gtceu.api.GTValues;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class GregicTinkeringConfig
{
	@RequiredArgsConstructor
	public enum DustItemSize
	{
		NORMAL(GTValues.M),
		SMALL(GTValues.M / 4),
		TINY(GTValues.M / 9);

		public final long size;
	}

	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.IntValue AIR_TANK_RECHARGE_RATE = BUILDER
		.comment("How quickly air ticks from the Air Tank modifier recharges per tick.")
		.defineInRange("air_tank_recharge_rate", 10, 1, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue AIR_CONSUMED_PER_ATTACK = BUILDER
		.comment("How many air ticks are consumed per melee attack.")
		.defineInRange("air_consumed_per_attack", 25, 0, Integer.MAX_VALUE);

	private static final ForgeConfigSpec.IntValue DUST_LOOT_CHANCE = BUILDER
		.comment("Chance per level out of 10000 for the Ore Grinder modifier to produce a dust.")
		.defineInRange("dust_loot_chance", 500, 1, 10000);

	private static final ForgeConfigSpec.EnumValue<DustItemSize> DUST_LOOT_DUST_ITEM_SIZE = BUILDER
		.comment("Size of the dust item produced by the dust loot.")
		.defineEnum("dust_loot_dust_item_size", DustItemSize.SMALL);

	static final ForgeConfigSpec SPEC = BUILDER.build();

	@Getter
	private static int airTankRechargeRate;

	@Getter
	private static int airConsumedPerAttack;

	@Getter
	private static int dustLootChance;

	@Getter
	private static DustItemSize dustLootDustItemSize;

	@SuppressWarnings("unused")
	static void onModConfig(final ModConfigEvent event)
	{
		airTankRechargeRate = AIR_TANK_RECHARGE_RATE.get();
		airConsumedPerAttack = AIR_CONSUMED_PER_ATTACK.get();
		dustLootChance = DUST_LOOT_CHANCE.get();
		dustLootDustItemSize = DUST_LOOT_DUST_ITEM_SIZE.get();
	}
}
