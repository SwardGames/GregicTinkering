package com.sward.gregictinkering.modifiers;

import com.sward.gregictinkering.GregicTinkeringConfig;
import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringTags;
import com.sward.gregictinkering.GregicTinkeringToolStats;
import com.sward.gregictinkering.player.IGregicTinkeringPlayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedContext;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AirFedModifier extends NoLevelsModifier implements
	ConditionalStatModifierHook,
	BreakSpeedModifierHook,
	MeleeDamageModifierHook,
	InventoryTickModifierHook
{
	public static final ResourceLocation STORED_AIR_KEY = GregicTinkeringMod.id("stored_air");

	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(
			this,
			ModifierHooks.CONDITIONAL_STAT,
			ModifierHooks.BREAK_SPEED,
			ModifierHooks.MELEE_DAMAGE,
			ModifierHooks.INVENTORY_TICK
		);
	}

	@Override
	public float modifyStat(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull LivingEntity living,
		@NotNull FloatToolStat stat,
		float baseValue,
		float multiplier
	)
	{
		if (stat == ToolStats.DRAW_SPEED && hasNoAir(tool, living))
		{
			return 0F;
		}

		return baseValue;
	}

	@Override
	public void onBreakSpeed(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull PlayerEvent.BreakSpeed event,
		@NotNull Direction sideHit,
		boolean isEffective,
		float miningSpeedModifier
	)
	{
		if (isEffective && hasNoAir(tool, event.getEntity()))
		{
			event.setNewSpeed(0F);
		}
	}

	@Override
	public float modifyBreakSpeed(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, @NotNull BreakSpeedContext context, float speed)
	{
		if (context.isEffective() && hasNoAir(tool, context.player()))
		{
			return 0F;
		}

		return BreakSpeedModifierHook.super.modifyBreakSpeed(tool, modifier, context, speed);
	}

	@Override
	public float getMeleeDamage(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull ToolAttackContext context,
		float baseDamage,
		float damage
	)
	{
		if (hasNoAirConsuming(tool, context.getAttacker(), GregicTinkeringConfig.getAirConsumedPerAttack()))
		{
			return baseDamage;
		}

		return damage;
	}

	@Override
	public void onInventoryTick(
		@NotNull IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull Level world,
		@NotNull LivingEntity holder,
		int itemSlot,
		boolean isSelected,
		boolean isCorrectSlot,
		@NotNull ItemStack stack
	)
	{
		if (entityHasNoAir(holder))
		{
			if (holder instanceof IGregicTinkeringPlayer player)
			{
				if (player.gregicTinkering$getGameMode().gregicTinkering$isDestroyingBlock())
				{
					int air = tool.getPersistentData().getInt(STORED_AIR_KEY);

					tool.getPersistentData().putInt(STORED_AIR_KEY, Math.max(air - 1, 0));
				}
			}
		}
		else
		{
			int air = Math.min(
				tool.getPersistentData().getInt(STORED_AIR_KEY) + GregicTinkeringConfig.getAirTankRechargeRate(),
				tool.getStats().getInt(GregicTinkeringToolStats.AIR_TANK_CAPACITY)
			);

			tool.getPersistentData().putInt(STORED_AIR_KEY, air);
		}
	}

	private static boolean hasNoAirConsuming(@NotNull IToolStackView tool, @NotNull Entity entity, int amount)
	{
		if (entityHasNoAir(entity))
		{
			int air = tool.getPersistentData().getInt(STORED_AIR_KEY);

			tool.getPersistentData().putInt(STORED_AIR_KEY, Math.max(air - amount, 0));

			return air <= 0;
		}

		return false;
	}

	private static boolean hasNoAir(@NotNull IToolStackView tool, @NotNull Entity entity)
	{
		return entityHasNoAir(entity) && hasNoStoredAir(tool);
	}

	private static boolean entityHasNoAir(@NotNull Entity entity)
	{
		return entity.isUnderWater() ||
		       entity.level().dimensionTypeRegistration().is(GregicTinkeringTags.DimensionTypes.NO_AIR);
	}

	private static boolean hasNoStoredAir(@NotNull IToolStackView tool)
	{
		int air = tool.getPersistentData().getInt(STORED_AIR_KEY);

		return air <= 0;
	}
}
