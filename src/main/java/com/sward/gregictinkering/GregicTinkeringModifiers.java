package com.sward.gregictinkering;

import com.sward.gregictinkering.energy.ElectricItemHandlerModifier;
import com.sward.gregictinkering.modifiers.AirFedModifier;
import com.sward.gregictinkering.modifiers.PoweredModifier;
import slimeknights.tconstruct.library.modifiers.util.DynamicModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class GregicTinkeringModifiers
{
	static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(GregicTinkeringMod.MOD_ID);

	public static final StaticModifier<ElectricItemHandlerModifier> ELECTRIC_ITEM_HANDLER = MODIFIERS.register("electric_item_handler", ElectricItemHandlerModifier::new);

	public static final StaticModifier<PoweredModifier> POWERED = MODIFIERS.register("powered", PoweredModifier::new);

	public static final StaticModifier<AirFedModifier> AIR_FED = MODIFIERS.register("air_fed", AirFedModifier::new);

	public static final DynamicModifier TRANSFORMER = MODIFIERS.registerDynamic("transformer");
}
