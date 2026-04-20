package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.tools.IBrokenTool;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.tables.recipe.CraftingTableRepairKitRecipe;

@Mixin(value = CraftingTableRepairKitRecipe.class, remap = false)
public abstract class CraftingTableRepairKitRecipeMixin
{
	@Inject(method = "assemble(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/core/RegistryAccess;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "RETURN", ordinal = 3), cancellable = true)
	private void onGetValidatedResult(
		CraftingContainer inv,
		RegistryAccess access,
		CallbackInfoReturnable<ItemStack> cir
	)
	{
		ItemStack originalResult = cir.getReturnValue();

		if (!originalResult.isEmpty())
		{
			if (originalResult.getItem() instanceof IBrokenTool brokenTool)
			{
				ItemStack resultStack = new ItemStack(brokenTool.getRepairedItem().get(), originalResult.getCount());
				resultStack.setTag(originalResult.getTag());

				cir.setReturnValue(resultStack);
			}
		}
	}
}