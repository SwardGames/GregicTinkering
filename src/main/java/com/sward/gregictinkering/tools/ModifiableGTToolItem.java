package com.sward.gregictinkering.tools;

import com.gregtechceu.gtceu.api.capability.CombinedCapabilityProvider;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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

public class ModifiableGTToolItem extends ModifiableItem implements IGTTool
{
	@Getter
	private final GTToolType toolType;

	private final boolean cancelSwing;

	public ModifiableGTToolItem(Properties properties, ToolDefinition toolDefinition, GTToolType toolType)
	{
		this(properties, toolDefinition, toolType, false);
	}

	public ModifiableGTToolItem(Properties properties, ToolDefinition toolDefinition, GTToolType toolType, boolean cancelSwing)
	{
		super(properties, toolDefinition);
		this.toolType = toolType;
		this.cancelSwing = cancelSwing;
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
	public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
	{
		return cancelSwing;
	}

	@Override
	public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction)
	{
		return super.canPerformAction(stack, toolAction) || (!ToolDamageUtil.isBroken(stack) && definition$canPerformAction(stack, toolAction));
	}

	@Override
	public @NotNull InteractionResult onItemUseFirst(@NotNull ItemStack stack, @NotNull UseOnContext context)
	{
		InteractionResult result = super.onItemUseFirst(stack, context);

		if (result == InteractionResult.PASS)
		{
			if (!ToolDamageUtil.isBroken(stack))
			{
				return definition$onItemUseFirst(stack, context);
			}
		}

		return result;
	}

	@Override
	public @NotNull InteractionResult useOn(@NotNull UseOnContext context)
	{
		InteractionResult result = super.useOn(context);

		if (result == InteractionResult.PASS)
		{
			if (!ToolDamageUtil.isBroken(context.getItemInHand()))
			{
				return definition$onItemUse(context);
			}
		}

		return result;
	}

	@Override
	public boolean onBlockStartBreak(@NotNull ItemStack stack, @NotNull BlockPos pos, @NotNull Player player)
	{
		boolean result = super.onBlockStartBreak(stack, pos, player);

		if (result)
		{
			//noinspection resource
			if (!player.level().isClientSide)
			{
				if (playSoundOnBlockDestroy())
				{
					playSound(player);
				}
			}
		}

		return result;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand hand)
	{
		InteractionResultHolder<ItemStack> result = super.use(worldIn, playerIn, hand);

		if (result.getResult() == InteractionResult.PASS)
		{
			if (!ToolDamageUtil.isBroken(playerIn.getItemInHand(hand)))
			{
				return definition$use(worldIn, playerIn, hand);
			}
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
		// Return true so that the electricity bar can be shown
		return true;
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

	@Override
	public ItemStack getRaw()
	{
		return new ItemStack(this);
	}

	@Override
	public ItemStack get()
	{
		ItemStack result = getRaw();
		ToolStack.ensureInitialized(result, getToolDefinition());
		return result;
	}

	@Override
	public ItemStack get(long defaultCharge, long defaultMaxCharge)
	{
		return get();
	}

	@Override
	public ItemStack get(long defaultMaxCharge)
	{
		return get();
	}

	@Override
	public long getCharge(ItemStack stack)
	{
		IElectricItem electricItem = GTCapabilityHelper.getElectricItem(stack);
		return electricItem != null ? electricItem.getCharge() : 0;
	}

	@Override
	public long getMaxCharge(ItemStack stack)
	{
		IElectricItem electricItem = GTCapabilityHelper.getElectricItem(stack);
		return electricItem != null ? electricItem.getMaxCharge() : 0;
	}

	@Override
	public float getMaterialToolSpeed()
	{
		return 0;
	}

	@Override
	public float getMaterialAttackDamage()
	{
		return 0;
	}

	@Override
	public float getMaterialAttackSpeed()
	{
		return 0;
	}

	@Override
	public int getMaterialDurability()
	{
		return 0;
	}

	@Override
	public int getMaterialEnchantability()
	{
		return 0;
	}

	@Override
	public int getMaterialHarvestLevel()
	{
		return 0;
	}

	@Override
	public float getTotalToolSpeed(ItemStack stack)
	{
		return 0;
	}

	@Override
	public float getTotalAttackDamage(ItemStack stack)
	{
		return 0;
	}

	@Override
	public float getTotalAttackSpeed(ItemStack stack)
	{
		return 0;
	}

	@Override
	public int getTotalMaxDurability(ItemStack stack)
	{
		return 0;
	}

	@Override
	public int getTotalEnchantability(ItemStack stack)
	{
		return 0;
	}

	@Override
	public int getTotalHarvestLevel(ItemStack stack)
	{
		return 0;
	}
}
