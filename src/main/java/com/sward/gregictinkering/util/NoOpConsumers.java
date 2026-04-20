package com.sward.gregictinkering.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoOpConsumers
{
	public static void LivingEntity(LivingEntity e) { }
}
