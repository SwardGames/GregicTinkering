package com.sward.gregictinkering.mixin;

import com.sward.gregictinkering.GregicTinkeringModifiers;
import com.sward.gregictinkering.modifiers.PoweredModifier;
import com.sward.gregictinkering.tools.ModifiableGTToolItem;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.List;

@Mixin(value = ShapelessRecipe.class)
public abstract class ShapelessRecipeMixin
{
	@Inject(
		method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z",
//		at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/Container;getItem(I)Lnet/minecraft/world/item/ItemStack;"),
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private void onMatches(
		CraftingContainer inv,
		Level level,
		CallbackInfoReturnable<Boolean> cir,
		StackedContents stackedcontents,
		List<ItemStack> inputs,
		int i,
		int j,
		ItemStack stack
	)
	{
		if (stack.getItem() instanceof ModifiableGTToolItem)
		{
			if (ToolDamageUtil.isBroken(stack))
			{
				cir.setReturnValue(false);
			}

			ToolStack toolStack = ToolStack.from(stack);

			if (toolStack.getModifier(GregicTinkeringModifiers.POWERED.getId()) != ModifierEntry.EMPTY && !PoweredModifier.isPowered(toolStack))
			{
				cir.setReturnValue(false);
			}
		}
	}
}
