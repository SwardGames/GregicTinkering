package com.sward.gregictinkering.materials.stats;

import slimeknights.mantle.data.loadable.primitive.EnumLoadable;

public enum FuelType
{
	EU,
	STEAM,
	DIESEL,
	GAS;

	public static final EnumLoadable<FuelType> LOADABLE = new EnumLoadable<>(FuelType.class);
}
