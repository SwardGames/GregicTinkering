package com.sward.gregictinkering.data.material;

import com.sward.gregictinkering.materials.stats.BatteryMaterialStats;
import com.sward.gregictinkering.materials.stats.EngineMaterialStats;
import com.sward.gregictinkering.materials.stats.FuelType;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;

import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;

public class PowerToolMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider
{
	public PowerToolMaterialStatsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials)
	{
		super(packOutput, materials);
	}

	@Override
	protected void addMaterialStats()
	{
		for (int i = 0; i < ENGINES.length; i++)
		{
			for (int fuelType = 0; fuelType < ENGINES[i].length; fuelType++)
			{
				addMaterialStats(
					ENGINES[i][fuelType],
					new EngineMaterialStats(
						fuelType == 0 ? 1F : (95 - 5 * i) / 100F,
						50 << i,
						(550 + 50 * i) / 1000F,
						(i < 2) ? -0.25F : 0.5F * i,
						FuelType.values()[fuelType]
					)
				);
			}
		}

		for (int i = 0; i < TANKS.length; i++)
		{
			addMaterialStats(
				TANKS[i][0],
				new BatteryMaterialStats(i == 0 ? 1000L : 2000L << i, 0, true)
			);
		}

		// Tier 1 (LV) Batteries
		addMaterialStats(SMALL_SODIUM_BATTERY, new BatteryMaterialStats(80_000L, 1, false));
		addMaterialStats(SMALL_CADMIUM_BATTERY, new BatteryMaterialStats(100_000L, 1, false));
		addMaterialStats(SMALL_LITHIUM_BATTERY, new BatteryMaterialStats(120_000L, 1, false));

		// Tier 2 (MV) Batteries
		addMaterialStats(MEDIUM_SODIUM_BATTERY, new BatteryMaterialStats(360_000L, 2, false));
		addMaterialStats(MEDIUM_CADMIUM_BATTERY, new BatteryMaterialStats(400_000L, 2, false));
		addMaterialStats(MEDIUM_LITHIUM_BATTERY, new BatteryMaterialStats(420_000L, 2, false));

		// Tier 3 (HV) Batteries
		addMaterialStats(LARGE_SODIUM_BATTERY, new BatteryMaterialStats(1_200_000L, 3, false));
		addMaterialStats(LARGE_CADMIUM_BATTERY, new BatteryMaterialStats(1_600_000L, 3, false));
		addMaterialStats(LARGE_LITHIUM_BATTERY, new BatteryMaterialStats(1_800_000L, 3, false));

		// Tier 4 (EV) Batteries
		addMaterialStats(SMALL_VANADIUM_BATTERY, new BatteryMaterialStats(10_240_000L, 4, false));

		// Tier 5 (IV) Batteries
		addMaterialStats(MEDIUM_VANADIUM_BATTERY, new BatteryMaterialStats(40_960_000L, 5, false));

		// Tier 6 (LuV) Batteries
		addMaterialStats(LARGE_VANADIUM_BATTERY, new BatteryMaterialStats(163_840_000L, 6, false));

		// Tier 7 (ZPM) Batteries
		addMaterialStats(MEDIUM_NAQUADRIA_BATTERY, new BatteryMaterialStats(655_360_000L, 7, false));

		// Tier 8 (UV) Batteries
		addMaterialStats(LARGE_NAQUADRIA_BATTERY, new BatteryMaterialStats(2_621_440_000L, 8, false));

		// Tier 9 (UHV) Batteries
		addMaterialStats(ULTIMATE_BATTERY, new BatteryMaterialStats(Long.MAX_VALUE, 9, false));
	}

	@Override
	public @NotNull String getName()
	{
		return "Gregic Tinkering Power Tool Material Stats";
	}
}
