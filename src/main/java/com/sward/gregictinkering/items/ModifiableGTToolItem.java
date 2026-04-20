package com.sward.gregictinkering.items;

import com.gregtechceu.gtceu.api.capability.CombinedCapabilityProvider;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.item.IGTTool;
import com.gregtechceu.gtceu.api.item.component.forge.IComponentCapability;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.item.tool.IGTToolDefinition;
import com.gregtechceu.gtceu.api.item.tool.behavior.IToolBehavior;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModifiableGTToolItem extends ModifiableItem implements IGTTool
{
	@Getter
	private final GTToolType toolType;

	private final Supplier<Item> brokenItem;

	public ModifiableGTToolItem(Properties properties, ToolDefinition toolDefinition, GTToolType toolType, Supplier<Item> brokenItem)
	{
		super(properties, toolDefinition);
		this.toolType = toolType;
		this.brokenItem = brokenItem;
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt)
	{
		List<ICapabilityProvider> providers = new ArrayList<>();
		providers.add(super.initCapabilities(stack, nbt));

		for (IToolBehavior behavior : this.getToolStats().getBehaviors())
		{
			if (behavior instanceof final IComponentCapability componentCapability)
			{
				providers.add(
					new ICapabilityProvider()
					{
						public <T> @NotNull LazyOptional<T> getCapability(
							@NotNull Capability<T> capability,
							@Nullable Direction arg
						)
						{
							return componentCapability.getCapability(stack, capability);
						}
					}
				);
			}
		}

		if (providers.size() == 1)
		{
			return providers.get(0);
		}
		else
		{
			return new CombinedCapabilityProvider(providers);
		}
	}

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction)
	{
		return super.canPerformAction(stack, toolAction) || definition$canPerformAction(stack, toolAction);
	}

	@Override
	public @NotNull InteractionResult onItemUseFirst(@NotNull ItemStack stack, @NotNull UseOnContext context)
	{
		InteractionResult result = super.onItemUseFirst(stack, context);

		if (result == InteractionResult.PASS)
		{
			return definition$onItemUseFirst(stack, context);
		}

		return result;
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context)
	{
		InteractionResult result = super.useOn(context);

		if (result == InteractionResult.PASS)
		{
			return definition$onItemUse(context);
		}

		return result;
	}

	@Override
	public boolean onBlockStartBreak(@NotNull ItemStack stack, @NotNull BlockPos pos, @NotNull Player player)
	{
		return super.onBlockStartBreak(stack, pos, player) || definition$onBlockStartBreak(stack, pos, player);
	}

	@Override
	public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving)
	{
		return super.mineBlock(stack, worldIn, state, pos, entityLiving) || definition$mineBlock(stack, worldIn, state, pos, entityLiving);
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand hand)
	{
		InteractionResultHolder<ItemStack> result = super.use(worldIn, playerIn, hand);

		if (result.getResult() == InteractionResult.PASS)
		{
			return definition$use(worldIn, playerIn, hand);
		}

		return result;
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player)
	{
		return definition$doesSneakBypassUse(stack, level, pos, player);
	}

	@Override
	public boolean shouldCauseBlockBreakReset(@NotNull ItemStack oldStack, @NotNull ItemStack newStack)
	{
		return super.shouldCauseBlockBreakReset(oldStack, newStack) || definition$shouldCauseBlockBreakReset(oldStack, newStack);
	}

	@Override
	public boolean shouldCauseReequipAnimation(@NotNull ItemStack oldStack, @NotNull ItemStack newStack, boolean slotChanged)
	{
		return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged) || definition$shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack)
	{
		return definition$hasCraftingRemainingItem(stack);
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemStack)
	{
		ItemStack stack = itemStack.copy();
		ToolStack tool = ToolStack.from(stack);
		Player player = ForgeHooks.getCraftingPlayer();

		ToolDamageUtil.damage(tool, 1, player, stack);

		if (tool.isBroken())
		{
			CompoundTag tag = stack.getTag();
			stack = new ItemStack(brokenItem.get(), stack.getCount());
			stack.setTag(tag);
		}

		this.playCraftingSound(player, stack);

		return stack;
	}

	@Override
	public Material getMaterial()
	{
		return GTMaterials.NULL;
	}

	@Override
	public boolean isElectric()
	{
		return false;
	}

	@Override
	public int getElectricTier()
	{
		return 0;
	}

	@Override
	public IGTToolDefinition getToolStats()
	{
		return toolType.toolDefinition;
	}

	@Override
	public SoundEntry getSound()
	{
		return toolType.soundEntry;
	}

	@Override
	public boolean playSoundOnBlockDestroy()
	{
		return toolType.playSoundOnBlockDestroy;
	}
}
