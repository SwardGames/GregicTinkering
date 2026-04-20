package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.tools.IBrokenTool;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.recipe.RecipeResult;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.tools.nbt.LazyToolStack;
import slimeknights.tconstruct.tables.recipe.TinkerStationRepairRecipe;

@Mixin(value = TinkerStationRepairRecipe.class, remap = false)
public abstract class TinkerStationRepairRecipeMixin
{
	@Inject(method = "getValidatedResult", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	private void onGetValidatedResult(
		ITinkerStationContainer inv,
		RegistryAccess access,
		CallbackInfoReturnable<RecipeResult<LazyToolStack>> cir
	)
	{
		RecipeResult<LazyToolStack> originalResult = cir.getReturnValue();

		if (originalResult.isSuccess())
		{
			LazyToolStack originalStack = originalResult.getResult();

			if (originalStack.getItem() instanceof IBrokenTool brokenTool)
			{
				ItemStack resultStack = new ItemStack(brokenTool.getRepairedItem().get(), originalStack.getSize());
				resultStack.setTag(originalStack.getStack().getTag());

				cir.setReturnValue(LazyToolStack.success(resultStack));
			}
		}
	}
}