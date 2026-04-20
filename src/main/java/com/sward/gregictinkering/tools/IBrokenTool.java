package com.sward.gregictinkering.tools;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface IBrokenTool
{
	@NotNull Supplier<Item> getRepairedItem();
}
