package com.sward.gregictinkering.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraftforge.fml.ModList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HasMod
{
	public static final boolean AD_ASTRA = ModList.get().isLoaded("ad_astra");
}
