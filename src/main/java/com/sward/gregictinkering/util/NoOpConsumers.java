package com.sward.gregictinkering.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoOpConsumers
{
	public static void LivingEntity(LivingEntity e) { }
}
