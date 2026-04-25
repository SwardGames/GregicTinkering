package com.sward.gregictinkering.compat;

import earth.terrarium.adastra.api.systems.OxygenApi;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AdAstraCompat
{
	public static boolean hasAirAtPosition(@NotNull Level level, @NotNull BlockPos pos)
	{
		return OxygenApi.API.hasOxygen(level, pos);
	}
}
