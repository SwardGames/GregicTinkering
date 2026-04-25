package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.player.IGregicTinkeringGameMode;
import com.sward.gregictinkering.player.IGregicTinkeringPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin implements IGregicTinkeringPlayer
{
	@Shadow
	@Final
	protected Minecraft minecraft;

	@Override
	public IGregicTinkeringGameMode gregicTinkering$getGameMode()
	{
		return (IGregicTinkeringGameMode) this.minecraft.gameMode;
	}
}
