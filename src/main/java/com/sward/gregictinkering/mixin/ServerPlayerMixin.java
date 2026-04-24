package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.player.IGregicTinkeringGameMode;
import com.sward.gregictinkering.player.IGregicTinkeringPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements IGregicTinkeringPlayer
{
	@Shadow
	@Final
	public ServerPlayerGameMode gameMode;

	@Override
	public IGregicTinkeringGameMode gregicTinkering$getGameMode()
	{
		return (IGregicTinkeringGameMode) this.gameMode;
	}
}
