package com.sward.gregictinkering.modifiers;

import com.sward.gregictinkering.GregicTinkeringTags;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
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
	MeleeDamageModifierHook
{
	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(
			this,
			ModifierHooks.CONDITIONAL_STAT,
			ModifierHooks.BREAK_SPEED,
			ModifierHooks.MELEE_DAMAGE
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
		if (stat == ToolStats.DRAW_SPEED && hasNoAir(living))
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
		if (isEffective && hasNoAir(event.getEntity()))
		{
			event.setNewSpeed(0F);
		}
	}

	@Override
	public float modifyBreakSpeed(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, @NotNull BreakSpeedContext context, float speed)
	{
		if (context.isEffective() && hasNoAir(context.player()))
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
		if (hasNoAir(context.getAttacker()))
		{
			return baseDamage;
		}

		return damage;
	}

	private static boolean hasNoAir(@NotNull Entity entity)
	{
		if (entity.isUnderWater())
		{
			return true;
		}

		if (entity.level().dimensionTypeRegistration().is(GregicTinkeringTags.DimensionTypes.NO_AIR))
		{
			return true;
		}

		return false;
	}
}
