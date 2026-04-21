package com.sward.gregictinkering;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringMaterialIds
{
	// Rubbers (For Plungers)
	public static final MaterialId RUBBER = id("rubber");

	// Engines
	// Tier 1 (LV)
	public static final MaterialId LV_ELECTRIC = id("lv_electric");
	public static final MaterialId BASIC_STEAM = id("basic_steam");
	public static final MaterialId BASIC_DIESEL = id("basic_diesel");
	public static final MaterialId BASIC_GAS = id("basic_gas");

	// Tier 2 (MV)
	public static final MaterialId MV_ELECTRIC = id("mv_electric");
	public static final MaterialId ADVANCED_STEAM = id("advanced_steam");
	public static final MaterialId ADVANCED_DIESEL = id("advanced_diesel");
	public static final MaterialId ADVANCED_GAS = id("advanced_gas");

	// Tier 3 (HV)
	public static final MaterialId HV_ELECTRIC = id("hv_electric");
	public static final MaterialId TURBO_STEAM = id("turbo_steam");
	public static final MaterialId TURBO_DIESEL = id("turbo_diesel");
	public static final MaterialId TURBO_GAS = id("turbo_gas");

	// Tier 4 (EV)
	public static final MaterialId EV_ELECTRIC = id("ev_electric");

	// Tier 5 (IV)
	public static final MaterialId IV_ELECTRIC = id("iv_electric");

	// Tier 6 (LuV)
	public static final MaterialId LUV_ELECTRIC = id("luv_electric");

	// Tier 7 (ZPM)
	public static final MaterialId ZPM_ELECTRIC = id("zpm_electric");

	// Tier 8 (UV)
	public static final MaterialId UV_ELECTRIC = id("uv_electric");

	// Batteries/Tanks
	// Tier 0 (Steam)
	public static final MaterialId BRONZE_FLUID_TANK = id("bronze_fluid_tank");

	// Tier 1 (LV)
	public static final MaterialId SMALL_SODIUM_BATTERY = id("small_sodium_battery");
	public static final MaterialId SMALL_CADMIUM_BATTERY = id("small_cadmium_battery");
	public static final MaterialId SMALL_LITHIUM_BATTERY = id("small_lithium_battery");
	public static final MaterialId STEEL_FLUID_TANK = id("steel_fluid_tank");

	// Tier 2 (MV)
	public static final MaterialId MEDIUM_SODIUM_BATTERY = id("medium_sodium_battery");
	public static final MaterialId MEDIUM_CADMIUM_BATTERY = id("medium_cadmium_battery");
	public static final MaterialId MEDIUM_LITHIUM_BATTERY = id("medium_lithium_battery");
	public static final MaterialId ALUMINIUM_FLUID_TANK = id("aluminium_fluid_tank");

	// Tier 3 (HV)
	public static final MaterialId LARGE_SODIUM_BATTERY = id("large_sodium_battery");
	public static final MaterialId LARGE_CADMIUM_BATTERY = id("large_cadmium_battery");
	public static final MaterialId LARGE_LITHIUM_BATTERY = id("large_lithium_battery");
	public static final MaterialId STAINLESS_STEEL_FLUID_TANK = id("stainless_steel_fluid_tank");

	// Tier 4 (EV)
	public static final MaterialId SMALL_VANADIUM_BATTERY = id("small_vanadium_battery");

	// Tier 5 (IV)
	public static final MaterialId MEDIUM_VANADIUM_BATTERY = id("medium_vanadium_battery");

	// Tier 6 (LuV)
	public static final MaterialId LARGE_VANADIUM_BATTERY = id("large_vanadium_battery");

	// Tier 7 (ZPM)
	public static final MaterialId MEDIUM_NAQUADRIA_BATTERY = id("medium_naquadria_battery");

	// Tier 8 (UV)
	public static final MaterialId LARGE_NAQUADRIA_BATTERY = id("large_naquadria_battery");

	// Tier 9 (UV)
	public static final MaterialId ULTIMATE_BATTERY = id("ultimate_battery");

	private static MaterialId id(String name)
	{
		return new MaterialId(GregicTinkeringMod.MOD_ID, name);
	}
}
