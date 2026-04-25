package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ElectricItemHandlerModifier;
import com.sward.gregictinkering.modifiers.AirFedModifier;
import com.sward.gregictinkering.modifiers.DustLootModifier;
import com.sward.gregictinkering.modifiers.PoweredModifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import slimeknights.tconstruct.library.modifiers.util.DynamicModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GregicTinkeringModifiers
{
	static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(GregicTinkeringMod.MOD_ID);

	public static final StaticModifier<ElectricItemHandlerModifier> ELECTRIC_ITEM_HANDLER = MODIFIERS.register("electric_item_handler", ElectricItemHandlerModifier::new);

	public static final StaticModifier<PoweredModifier> POWERED = MODIFIERS.register("powered", PoweredModifier::new);

	public static final StaticModifier<AirFedModifier> AIR_FED = MODIFIERS.register("air_fed", AirFedModifier::new);

	public static final DynamicModifier TRANSFORMER = MODIFIERS.registerDynamic("transformer");

	public static final StaticModifier<DustLootModifier> DUST_LOOT = MODIFIERS.register("dust_loot", DustLootModifier::new);
}
