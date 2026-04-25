package com.sward.gregictinkering.modifiers;

import com.sward.gregictinkering.GregicTinkeringConfig;
import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringTags;
import com.sward.gregictinkering.GregicTinkeringToolStats;
import com.sward.gregictinkering.modifierhooks.HasPowerModifierHook;
import com.sward.gregictinkering.player.IGregicTinkeringPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.DamageDealtModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class AirFedModifier extends NoLevelsModifier implements
	InventoryTickModifierHook,
	DamageDealtModifierHook,
	HasPowerModifierHook
{
	public static final ResourceLocation STORED_AIR_KEY = GregicTinkeringMod.id("stored_air");

	@Override
	protected void registerHooks(ModuleHookMap.Builder hookBuilder)
	{
		hookBuilder.addHook(
			this,
			ModifierHooks.INVENTORY_TICK,
			ModifierHooks.DAMAGE_DEALT,
			PoweredModifier.HOOK
		);
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
			if (holder.getMainHandItem() == stack || holder.getOffhandItem() == stack)
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

	@Override
	public void onDamageDealt(
		IToolStackView tool,
		@NotNull ModifierEntry modifier,
		@NotNull EquipmentContext context,
		@NotNull EquipmentSlot slotType,
		@NotNull LivingEntity target,
		@NotNull DamageSource source,
		float amount,
		boolean isDirectDamage
	)
	{
		int air = tool.getPersistentData().getInt(STORED_AIR_KEY);

		tool.getPersistentData().putInt(STORED_AIR_KEY, Math.max(air - 1, 0));
	}

	@Override
	public boolean hasPower(
		@NotNull IToolStackView tool,
		@Nullable ModifierEntry modifier,
		@Nullable LivingEntity holder
	)
	{
		return holder == null || !(entityHasNoAir(holder) && hasNoStoredAir(tool));
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
