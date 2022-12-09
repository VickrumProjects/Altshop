package me.vickrum.alts.extra;

import com.massivecraft.massivecore.util.InventoryUtil;
import com.massivecraft.massivecore.util.MUtil;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class NbtUtil {

	private final static List<String> DEFAULT_NBT_TAGS = MUtil.list("id", "Count", "Damage", "tag", "ench", "HideFlags",
			"Unbreakable", "display");

	// -------------------------------------------- //
	// SET NBT
	// -------------------------------------------- //

	public static ItemStack setSerializedNbt(ItemStack itemStack, Map<String, String> serializedNbt) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		for (Map.Entry<String, String> entry : serializedNbt.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String[] split = value.split(":");
			String obj = split[1];

			switch (split[0]) {
			case "D":
				itemStack = setKey(itemStack, key, Double.parseDouble(obj));
				break;
			case "S":
				itemStack = setKey(itemStack, key, obj);
				break;
			case "L":
				itemStack = setKey(itemStack, key, Long.parseLong(obj));
				break;
			case "I":
				itemStack = setKey(itemStack, key, Integer.parseInt(obj));
				break;
			}
		}

		return itemStack;
	}

	public ItemStack setKeys(ItemStack itemStack, Map<String, Object> nbt) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		nbt.forEach(nbtItem::setObject);

		return nbtItem.getItem();
	}

	public static ItemStack setKey(ItemStack itemStack, String key, String value) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		nbtItem.setString(key, value);

		return nbtItem.getItem();
	}

	public static ItemStack setKey(ItemStack itemStack, String key, int value) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		nbtItem.setInteger(key, value);

		return nbtItem.getItem();
	}

	public static ItemStack setKey(ItemStack itemStack, String key, long value) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		nbtItem.setLong(key, value);

		return nbtItem.getItem();
	}

	public static ItemStack setKey(ItemStack itemStack, String key, double value) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		nbtItem.setDouble(key, value);

		return nbtItem.getItem();
	}

	// -------------------------------------------- //
	// HAS NBT
	// -------------------------------------------- //

	public boolean hasKey(ItemStack itemStack, String... keys) {
		if (InventoryUtil.isNothing(itemStack))
			return false;

		NBTItem nbtItem = new NBTItem(itemStack);
		boolean containsAll = true;

		for (String key : keys) {
			Boolean has = nbtItem.hasKey(key);

			if (has == null || !has) {
				containsAll = false;
				break;
			}
		}

		return containsAll;
	}

	// -------------------------------------------- //
	// REMOVE NBT
	// -------------------------------------------- //

	public ItemStack removeKey(ItemStack itemStack, String... keys) {
		if (InventoryUtil.isNothing(itemStack))
			return itemStack;

		NBTItem nbtItem = new NBTItem(itemStack);

		Arrays.stream(keys).forEach(nbtItem::removeKey);

		return nbtItem.getItem();
	}

	// -------------------------------------------- //
	// GET NBT
	// -------------------------------------------- //

	public Set<String> getKeySet(ItemStack itemStack) {
		if (InventoryUtil.isNothing(itemStack))
			return new HashSet<>();

		NBTItem nbtItem = new NBTItem(itemStack);

		return nbtItem.getKeys();
	}

	public static Map<String, Object> getNbtValues(ItemStack itemStack) {
		if (InventoryUtil.isNothing(itemStack))
			return new HashMap<>();

		NBTItem nbtItem = new NBTItem(itemStack);
		Map<String, Object> objectMap = new HashMap<>();

		nbtItem.getKeys().forEach(key -> {
			Object object = getValue(nbtItem, key);

			if (object == null)
				return;

			objectMap.put(key, object);
		});

		return objectMap;
	}

	public static Map<String, String> getSerializedNbtValues(ItemStack itemStack) {
		if (InventoryUtil.isNothing(itemStack))
			return null;

		Map<String, Object> nbtData = getNbtValues(itemStack);

		if (nbtData.isEmpty())
			return null;

		Map<String, String> map = new HashMap<>();

		nbtData.forEach((key, data) -> {
			if (DEFAULT_NBT_TAGS.contains(key))
				return;

			if (data instanceof Integer)
				map.put(key, "I:" + data);
			else if (data instanceof Long)
				map.put(key, "L:" + data);
			else if (data instanceof Double)
				map.put(key, "D:" + data);
			else if (data instanceof String)
				map.put(key, "S:" + data);
		});

		return map;
	}

	public String getKeyStringValue(ItemStack itemStack, String key) {
		if (InventoryUtil.isNothing(itemStack))
			return "";

		NBTItem nbtItem = new NBTItem(itemStack);

		return nbtItem.getString(key);
	}

	public int getKeyIntValue(ItemStack itemStack, String key) {
		if (InventoryUtil.isNothing(itemStack))
			return 0;

		NBTItem nbtItem = new NBTItem(itemStack);

		Integer integer = nbtItem.getInteger(key);

		return integer == null ? 0 : integer;
	}

	public long getKeyLongValue(ItemStack itemStack, String key) {
		if (InventoryUtil.isNothing(itemStack))
			return 0L;

		NBTItem nbtItem = new NBTItem(itemStack);

		Long longg = nbtItem.getLong(key);

		return longg == null ? 0 : longg;
	}

	public double getKeyDoubleValue(ItemStack itemStack, String key) {
		if (InventoryUtil.isNothing(itemStack))
			return 0D;

		NBTItem nbtItem = new NBTItem(itemStack);

		Double doublee = nbtItem.getDouble(key);

		return doublee == null ? 0 : doublee;
	}

	private static Object getValue(NBTItem nbtItem, String key) {
		Double doublee = nbtItem.getDouble(key);
		String stringg = nbtItem.getString(key);
		Long longg = nbtItem.getLong(key);
		Integer integer = nbtItem.getInteger(key);

		if (integer != null)
			return integer;
		if (longg != null)
			return longg;
		if (doublee != null)
			return doublee;

		return stringg;
	}
}