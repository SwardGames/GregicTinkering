package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.tools.ModifiableGTToolItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slimeknights.tconstruct.library.recipe.modifiers.adding.OverslimeCraftingTableRecipe;

@Mixin(value = OverslimeCraftingTableRecipe.class)
public class OverslimeCraftingTableRecipeMixin
{
	@Inject(
		method = "getRemainingItems(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/item/crafting/Ingredient;II)Lnet/minecraft/core/NonNullList;",
		at = @At(value = "RETURN"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		remap = false
	)
	private static void gregicTinkering$getRemainingItems(
		CraftingContainer inv,
		Ingredient ingredient,
		int repairNeeded,
		int repairPerItem,
		CallbackInfoReturnable<NonNullList<ItemStack>> cir,
		NonNullList<ItemStack> list
	)
	{
		for (ItemStack stack : list)
		{
			if (stack.getItem() instanceof ModifiableGTToolItem)
			{
				stack.setCount(0);
			}
		}
	}
}
