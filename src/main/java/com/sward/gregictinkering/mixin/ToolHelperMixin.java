package com.sward.gregictinkering.mixin;

import com.gregtechceu.gtceu.api.item.tool.ToolHelper;
import com.sward.gregictinkering.util.NoOpConsumers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;

@Mixin(value = ToolHelper.class, remap = false)
public abstract class ToolHelperMixin
{
	@Inject(method = "damageItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;I)V", at = @At("HEAD"), cancellable = true)
	private static void onDamageItem(
		@NotNull ItemStack stack,
		@Nullable LivingEntity user,
		int damage,
		CallbackInfo callbackInfo
	)
	{
		if (stack.getItem() instanceof ModifiableItem)
		{
			if (user != null)
			{
				stack.hurtAndBreak(damage, user, NoOpConsumers::LivingEntity);
			}

			callbackInfo.cancel();
		}
	}
}
