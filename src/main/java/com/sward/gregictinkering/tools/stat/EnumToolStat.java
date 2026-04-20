package com.sward.gregictinkering.tools.stat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.data.predicate.IJsonPredicate;
import slimeknights.mantle.data.predicate.item.ItemPredicate;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;

import java.util.Locale;

public class EnumToolStat<E extends Enum<E>> implements IToolStat<E>
{
	private final Class<E> enumClass;

	private final E[] enumConstants;

	/**
	 * Name of this tool stat
	 */
	@Getter
	private final ToolStatId name;

	/**
	 * Gets the default value for this stat
	 */
	private final E defaultValue;

	private final IJsonPredicate<Item> items;

	public EnumToolStat(
		Class<E> enumClass,
		ToolStatId name,
		E defaultValue,
		IJsonPredicate<Item> items
	)
	{
		this.enumClass = enumClass;
		this.enumConstants = enumClass.getEnumConstants();
		this.name = name;
		this.defaultValue = defaultValue;
		this.items = items;
	}

	public EnumToolStat(
		Class<E> enumClass,
		ToolStatId name,
		E defaultValue,
		@Nullable TagKey<Item> tag
	)
	{
		this(enumClass, name, defaultValue, tag == null ? ItemPredicate.ANY : ItemPredicate.tag(tag));
	}

	public EnumToolStat(Class<E> enumClass, ToolStatId name, E defaultValue)
	{
		this(enumClass, name, defaultValue, ItemPredicate.ANY);
	}

	@Override
	public @NotNull E getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public boolean supports(@NotNull Item item)
	{
		return items.matches(item);
	}

	@Override
	public @NotNull Object makeBuilder()
	{
		return new EnumBuilder(defaultValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public @NotNull E build(@NotNull ModifierStatsBuilder parent, @NotNull Object builder)
	{
		return ((EnumBuilder)builder).value;
	}

	@Override
	public void update(ModifierStatsBuilder builder, @NotNull E value)
	{
		builder.<EnumBuilder>updateStat(
			this, b ->
			{
				b.value = value;
			}
		);
	}

	@Override
	public @Nullable E read(@NotNull Tag tag)
	{
		return parse(tag.getAsString());
	}

	@Override
	public @Nullable Tag write(@NotNull E value)
	{
		return StringTag.valueOf(value.name().toLowerCase(Locale.ROOT));
	}

	@Override
	public @NotNull E deserialize(@NotNull JsonElement json)
	{
		E result = parse(GsonHelper.convertToString(json, getName().toString()));

		if (result == null)
		{
			String var10002 = this.enumClass.getSimpleName();
			throw new JsonSyntaxException("Invalid " + var10002 + " " + name);
		}

		return result;
	}

	@Override
	public @NotNull JsonElement serialize(@NotNull E value)
	{
		return new JsonPrimitive(value.toString().toLowerCase(Locale.ROOT));
	}

	@Override
	public @NotNull E fromNetwork(@NotNull FriendlyByteBuf buffer)
	{
		return buffer.readEnum(enumClass);
	}

	@Override
	public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull E value)
	{
		buffer.writeEnum(value);
	}

	@Override
	public @NotNull Component formatValue(@NotNull E value)
	{
		return Component.translatable(getTranslationKey() + "." + value.toString().toLowerCase(Locale.ROOT));
	}

	private @Nullable E parse(@NotNull String string)
	{
		for (E value : this.enumConstants)
		{
			if (value.name().toLowerCase(Locale.ROOT).equals(string))
			{
				return value;
			}
		}

		return null;
	}

	/**
	 * Internal builder to store the add and multiply value
	 */
	protected class EnumBuilder
	{
		public E value;

		public EnumBuilder(E value)
		{
			this.value = value;
		}
	}
}
