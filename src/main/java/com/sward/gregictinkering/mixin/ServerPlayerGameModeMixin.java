package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.player.IGregicTinkeringGameMode;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin implements IGregicTinkeringGameMode
{
	@Shadow
	private boolean isDestroyingBlock;

	@Override
	public boolean gregicTinkering$isDestroyingBlock()
	{
		return isDestroyingBlock;
	}
}
