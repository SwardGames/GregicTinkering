package com.sward.gregictinkering.tools.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.util.RegistryHelper;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.function.LongFunction;

public class MaxLongToolStat implements IToolStat<Long>
{
	private final ToolStatId name;
	private final long defaultValue;
	private final LongFunction<Component> displayName;

	@Nullable
	private final TagKey<Item> tag;

	public MaxLongToolStat(ToolStatId name, long defaultValue, LongFunction<Component> displayName)
	{
		this(name, defaultValue, displayName, null);
	}

	@Override
	public boolean supports(@NotNull Item item)
	{
		return this.tag == null || RegistryHelper.contains(this.tag, item);
	}

	@Override
	public @NotNull Long getDefaultValue()
	{
		return this.defaultValue;
	}

	public long clamp(long value)
	{
		return Math.max(value, 0L);
	}

	@Override
	public @NotNull TierBuilder makeBuilder()
	{
		return new TierBuilder();
	}

	@Override
	public void update(ModifierStatsBuilder builder, @NotNull Long value)
	{
		builder.updateStat(this, (b) -> ((TierBuilder)b).tier = Math.max(((TierBuilder)b).tier, value));
	}

	@Override
	public @NotNull Long build(@NotNull ModifierStatsBuilder parent, @NotNull Object builder)
	{
		return ((TierBuilder) builder).tier;
	}

	@Override
	@Nullable
	public Long read(@NotNull Tag tag)
	{
		return TagUtil.isNumeric(tag) ? ((NumericTag) tag).getAsLong() : null;
	}

	@Override
	@Nullable
	public Tag write(@NotNull Long value)
	{
		return LongTag.valueOf(value);
	}

	@Override
	public @NotNull Long deserialize(@NotNull JsonElement json)
	{
		return GsonHelper.convertToLong(json, this.getName().toString());
	}

	@Override
	public @NotNull JsonElement serialize(@NotNull Long value)
	{
		return new JsonPrimitive(value);
	}

	@Override
	public @NotNull Long fromNetwork(FriendlyByteBuf buffer)
	{
		return buffer.readVarLong();
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, @NotNull Long value)
	{
		buffer.writeVarLong(value);
	}

	@Override
	public @NotNull Component formatValue(@NotNull Long number)
	{
		return Component.translatable(Util.makeTranslationKey("tool_stat", this.getName())).append(this.displayName.apply(number));
	}

	public String toString()
	{
		return "MaxLongToolStat{" + this.name + "}";
	}

	public MaxLongToolStat(ToolStatId name, long defaultValue, LongFunction<Component> displayName, @Nullable TagKey<Item> tag)
	{
		this.name = name;
		this.defaultValue = defaultValue;
		this.displayName = displayName;
		this.tag = tag;
	}

	@Override
	public @NotNull ToolStatId getName()
	{
		return this.name;
	}

	public static class TierBuilder
	{
		private long tier = 0;

		protected TierBuilder()
		{
		}
	}
}
