package com.sward.gregictinkering.mixin;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.client.renderer.item.ToolChargeBarRenderer;
import com.gregtechceu.gtceu.client.renderer.item.decorator.GTToolBarRenderer;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

@Mixin(value = GTToolBarRenderer.class, remap = false)
public abstract class GTToolBarRendererMixin
{
	@Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
	private void render(
		GuiGraphics guiGraphics,
		Font font,
		ItemStack stack,
		int x,
		int y,
		CallbackInfoReturnable<Boolean> cir
	)
	{
		if (stack.getItem() instanceof ModifiableItem)
		{
			ToolStack tool = ToolStack.from(stack);

			boolean renderDurability = !tool.isUnbreakable();

			if (renderDurability)
			{
				int leftColor = stack.getBarColor();
				leftColor |= 0xFF000000;
				int rightColor = FastColor.ARGB32.lerp(0.5F, leftColor, -1);

				ToolChargeBarRenderer.render(guiGraphics, stack.getBarWidth(), x, y, 0, true, leftColor, rightColor, false);
			}

			IElectricItem electricItem = GTCapabilityHelper.getElectricItem(stack);

			if (electricItem != null)
			{
				ToolChargeBarRenderer.renderElectricBar(
					guiGraphics,
					electricItem.getCharge(),
					electricItem.getMaxCharge(),
					x,
					y,
					renderDurability
				);
			}
			else
			{
				int capacity = ToolTankHelper.TANK_HELPER.getCapacity(tool);

				if (capacity != 0)
				{
					FluidStack fluid = ToolTankHelper.TANK_HELPER.getFluid(tool);

					if (!fluid.isEmpty())
					{
						int level = Math.round(fluid.getAmount() * 13.0F / capacity);

						int leftColor = -1;
						int rightColor = -1;

						Material material = ChemicalHelper.getMaterial(fluid.getFluid());

						if (material != GTMaterials.NULL)
						{
							leftColor = material.getMaterialRGB() | 0xFF000000;
						}

						if (leftColor == -1)
						{
							leftColor = 0xFFF7E03F;
							rightColor = 0xFFFFFFDD;
						}
						else
						{
							rightColor = FastColor.ARGB32.lerp(0.5F, leftColor, -1);
						}

						ToolChargeBarRenderer.render(
							guiGraphics,
							level,
							x,
							y,
							renderDurability ? 2 : 0,
							true,
							leftColor,
							rightColor,
							false
						);

						// Render as an electric bar for now
						// Later I will figure out how to get a fluids color
//						ToolChargeBarRenderer.renderElectricBar(
//							guiGraphics,
//							fluid.getAmount(),
//							capacity,
//							x,
//							y,
//							renderDurability
//						);
					}
				}
			}

			cir.setReturnValue(true);
		}
	}
}
