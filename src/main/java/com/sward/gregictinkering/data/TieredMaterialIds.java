package com.sward.gregictinkering.data;

import slimeknights.tconstruct.library.materials.definition.MaterialId;
import static slimeknights.tconstruct.tools.data.material.MaterialIds.*;
import static com.sward.gregictinkering.GregicTinkeringMaterialIds.*;

public final class TieredMaterialIds
{
	public static final MaterialId[][] METALS = {
		{

		},
		{
			copper
		},
		{
			iron,
			osmium,
			silver,
			lead,
			aluminum
		},
		{
			slimesteel,
			amethystBronze,
			pigIron,
			roseGold,
			cobalt,
			steel,
			electrum,
			bronze,
			constantan,
			invar,
			pewter
		},
		{
			cinderslime,
			queensSlime,
			hepatizon,
			manyullyn,
			knightmetal,
			fiery
		}
	};

	public static final MaterialId[][] HEAD_METALS = {
		{

		},
		{
			copper
		},
		{
			iron,
			osmium,
			silver,
			lead
		},
		{
			slimesteel,
			amethystBronze,
			pigIron,
			roseGold,
			cobalt,
			steel,
			electrum,
			bronze,
			constantan,
			invar,
			pewter
		},
		{
			cinderslime,
			queensSlime,
			hepatizon,
			manyullyn,
			knightmetal,
			fiery
		}
	};

	public static final MaterialId[][] WOODS = {
		{
			wood
		},
		{
			chorus
		},
		{
			slimewood,
			treatedWood,
			ironwood
		},
		{
			nahuatl,
			platedSlimewood,
			steeleaf
		},
		{
		}
	};

	public static final MaterialId[][] BONES = {
		{

		},
		{
			bone
		},
		{
			venombone
		},
		{
			necronium
		},
		{
			blazingBone
		}
	};

	public static final MaterialId[][] ENGINES = {
		{

		},
		{
			LV_ELECTRIC,
			BASIC_STEAM,
			BASIC_DIESEL,
			BASIC_GAS
		},
		{
			MV_ELECTRIC,
			ADVANCED_STEAM,
			ADVANCED_DIESEL,
			ADVANCED_GAS
		},
		{
			HV_ELECTRIC,
			TURBO_STEAM,
			TURBO_DIESEL,
			TURBO_GAS
		},
		{
			EV_ELECTRIC
		},
		{
			IV_ELECTRIC
		},
		{
			LUV_ELECTRIC
		},
		{
			ZPM_ELECTRIC
		},
		{
			UV_ELECTRIC
		}
	};

	public static final MaterialId[][] BATTERIES = {
		{

		},
		{
			SMALL_SODIUM_BATTERY,
			SMALL_CADMIUM_BATTERY,
			SMALL_LITHIUM_BATTERY,
		},
		{
			MEDIUM_SODIUM_BATTERY,
			MEDIUM_CADMIUM_BATTERY,
			MEDIUM_LITHIUM_BATTERY,
		},
		{
			LARGE_SODIUM_BATTERY,
			LARGE_CADMIUM_BATTERY,
			LARGE_LITHIUM_BATTERY,
		},
		{
			SMALL_VANADIUM_BATTERY
		},
		{
			MEDIUM_VANADIUM_BATTERY
		},
		{
			LARGE_VANADIUM_BATTERY
		},
		{
			MEDIUM_NAQUADRIA_BATTERY
		},
		{
			LARGE_NAQUADRIA_BATTERY
		},
		{
			ULTIMATE_BATTERY
		}
	};

	public static final MaterialId[][] TANKS = {
		{
			BRONZE_FLUID_TANK
		},
		{
			STEEL_FLUID_TANK
		},
		{
			ALUMINIUM_FLUID_TANK
		},
		{
			STAINLESS_STEEL_FLUID_TANK
		}
	};
}
