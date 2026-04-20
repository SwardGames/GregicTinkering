package com.sward.gregictinkering.energy;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.sward.gregictinkering.GregicTinkeringTags;
import com.sward.gregictinkering.GregicTinkeringMod;
import com.sward.gregictinkering.GregicTinkeringModifiers;
import com.sward.gregictinkering.tools.stat.LongToolStat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierTraitModule;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.MaxToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;

import java.util.function.Supplier;

public record ToolElectricItemCapability(Supplier<? extends IToolStackView> tool) implements IElectricItem
{
	public static final Component POWER_TIER_COMPONENT = Component.translatable("tool_stat.gregic_tinkering.power_tier");

	/** Stat marking the max charge */
	public static final LongToolStat MAX_CHARGE_STAT = new LongToolStat(new ToolStatId(GregicTinkeringMod.MOD_ID, "max_charge"), 0xFF54FC54, 0L, 0L, Long.MAX_VALUE, GregicTinkeringTags.Items.POWER_TOOL);

	/** Stat marking the power tier */
	public static final MaxToolStat POWER_TIER_STAT = new MaxToolStat(new ToolStatId(GregicTinkeringMod.MOD_ID, "power_tier"), 0, value -> POWER_TIER_COMPONENT.copy().append(Component.literal(GTValues.VNF[value])));

	/** Persistent data key for fetching the current charge */
	public static final ResourceLocation CHARGE_KEY = GregicTinkeringMod.id("charge");

	/** Include this module in a modifier adding electric item capacity or functionality to ensure capacity changes are properly cleaned up */
	public static final ModifierModule ELECTRIC_ITEM_HANDLER = new ModifierTraitModule(GregicTinkeringModifiers.ELECTRIC_ITEM_HANDLER.getId(), 1, true);

	public static int getTier(IToolStackView tool)
	{
		return tool.getStats().getInt(POWER_TIER_STAT);
	}

	public static long getMaxCharge(IToolStackView tool)
	{
		return tool.getStats().get(MAX_CHARGE_STAT);
	}

	public static long getCharge(IToolStackView tool)
	{
		return tool.getPersistentData().get(CHARGE_KEY, CompoundTag::getLong);
	}

	public static void setCharge(IToolStackView tool, long energy)
	{
		setChargeRaw(tool, Math.max(energy, 0L));
	}

	private static void setChargeRaw(IToolStackView tool, long energy)
	{
		if (energy == 0L)
		{
			tool.getPersistentData().remove(CHARGE_KEY);
		}
		else
		{
			tool.getPersistentData().put(CHARGE_KEY, LongTag.valueOf(energy));
		}
	}

	public static void checkCharge(IToolStackView tool)
	{
		long energy = getCharge(tool);

		if (energy < 0L)
		{
			setChargeRaw(tool, 0L);
		}
		else
		{
			long capacity = getMaxCharge(tool);

			if (energy > capacity)
			{
				setChargeRaw(tool, capacity);
			}
		}
	}

	@Override
	public boolean canProvideChargeExternally()
	{
		return false;
	}

	@Override
	public boolean chargeable()
	{
		return true;
	}

	@Override
	public long charge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean simulate)
	{
		IToolStackView tool = this.tool.get();

		if (amount > 0L && chargerTier <= getTier(tool))
		{
			long canReceive = this.getMaxCharge() - this.getCharge();

			if (!ignoreTransferLimit)
			{
				amount = Math.min(amount, this.getTransferLimit());
			}

			long charged = Math.min(amount, canReceive);

			if (!simulate)
			{
				setChargeRaw(tool, getCharge(tool) + charged);
			}

			return charged;
		}
		else
		{
			return 0L;
		}
	}

	@Override
	public long discharge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean externally, boolean simulate)
	{
		IToolStackView tool = this.tool.get();

		if ((!externally || amount == Long.MAX_VALUE) && chargerTier <= getTier(tool) && amount > 0L)
		{
			if (!ignoreTransferLimit)
			{
				amount = Math.min(amount, this.getTransferLimit());
			}

			long charge = this.getCharge();
			long discharged = Math.min(amount, charge);

			if (!simulate)
			{
				setChargeRaw(tool, charge - discharged);
			}

			return discharged;
		}
		else
		{
			return 0L;
		}
	}

	@Override
	public long getTransferLimit()
	{
		return GTValues.VEX[getTier(this.tool.get())];
	}

	@Override
	public long getMaxCharge()
	{
		return getMaxCharge(this.tool.get());
	}

	@Override
	public long getCharge()
	{
		return getCharge(this.tool.get());
	}

	@Override
	public int getTier()
	{
		// Limit reported tier to prevent crash in chargers.
		return Math.min(14, getTier(this.tool.get()));
	}

	public static class Provider implements ToolCapabilityProvider.IToolCapabilityProvider
	{
		private final LazyOptional<IElectricItem> electricItemCap;

		public Provider(Supplier<? extends IToolStackView> toolStack)
		{
			this.electricItemCap = LazyOptional.of(() -> new ToolElectricItemCapability(toolStack));
		}

		@Override
		public <T> @NotNull LazyOptional<T> getCapability(@NotNull IToolStackView tool, @NotNull Capability<T> cap)
		{
			return cap == GTCapability.CAPABILITY_ELECTRIC_ITEM && tool.getStats().get(ToolElectricItemCapability.MAX_CHARGE_STAT) > 0 ? this.electricItemCap.cast() : LazyOptional.empty();
		}
	}
}
