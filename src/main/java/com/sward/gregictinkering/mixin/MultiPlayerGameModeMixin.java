package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.player.IGregicTinkeringGameMode;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin implements IGregicTinkeringGameMode
{
	@Shadow
	private boolean isDestroying;

	@Override
	public boolean gregicTinkering$isDestroyingBlock()
	{
		return isDestroying;
	}
}
