package me.vickrum.alts.extra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.inventory.ItemFlag;

public class MetaWrapper {
	private Map<String, Integer> enchants;

	private Map<String, String> nbtMap;

	private Set<ItemFlag> itemFlags;

	private String displayName;

	private List<String> lore;

	private Boolean unbreakable;

	public static MetaWrapperBuilder builder() {
		return new MetaWrapperBuilder();
	}

	public static class MetaWrapperBuilder {
		private Map<String, Integer> enchants;

		private Map<String, String> nbtMap;

		private Set<ItemFlag> itemFlags;

		private String displayName;

		private List<String> lore;

		private Boolean unbreakable;

		public MetaWrapperBuilder enchants(Map<String, Integer> enchants) {
			this.enchants = enchants;
			return this;
		}

		public MetaWrapperBuilder nbtMap(Map<String, String> nbtMap) {
			this.nbtMap = nbtMap;
			return this;
		}

		public MetaWrapperBuilder itemFlags(Set<ItemFlag> itemFlags) {
			this.itemFlags = itemFlags;
			return this;
		}

		public MetaWrapperBuilder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		public MetaWrapperBuilder lore(List<String> lore) {
			this.lore = lore;
			return this;
		}

		public MetaWrapperBuilder unbreakable(Boolean unbreakable) {
			this.unbreakable = unbreakable;
			return this;
		}

		public MetaWrapper build() {
			return new MetaWrapper(this.enchants, this.nbtMap, this.itemFlags, this.displayName, this.lore,
					this.unbreakable);
		}

		public String toString() {
			return "MetaWrapper.MetaWrapperBuilder(enchants=" + this.enchants + ", nbtMap=" + this.nbtMap
					+ ", itemFlags=" + this.itemFlags + ", displayName=" + this.displayName + ", lore=" + this.lore
					+ ", unbreakable=" + this.unbreakable + ")";
		}
	}

	public MetaWrapper() {
	}

	public MetaWrapper(Map<String, Integer> enchants, Map<String, String> nbtMap, Set<ItemFlag> itemFlags,
			String displayName, List<String> lore, Boolean unbreakable) {
		this.enchants = enchants;
		this.nbtMap = nbtMap;
		this.itemFlags = itemFlags;
		this.displayName = displayName;
		this.lore = lore;
		this.unbreakable = unbreakable;
	}

	public Map<String, Integer> getEnchants() {
		return this.enchants;
	}

	public void setEnchants(Map<String, Integer> enchants) {
		this.enchants = enchants;
	}

	public Map<String, String> getNbtMap() {
		return this.nbtMap;
	}

	public void setNbtMap(Map<String, String> nbtMap) {
		this.nbtMap = nbtMap;
	}

	public Set<ItemFlag> getItemFlags() {
		return this.itemFlags;
	}

	public void setItemFlags(Set<ItemFlag> itemFlags) {
		this.itemFlags = itemFlags;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public void addEnchant(String enchant, int level) {
		if (this.enchants == null)
			this.enchants = new HashMap<>();
		this.enchants.put(enchant, Integer.valueOf(level));
	}

	public void addItemFlags(ItemFlag... itemFlags) {
		if (this.itemFlags == null)
			this.itemFlags = new HashSet<>();
		this.itemFlags.addAll(Arrays.asList(itemFlags));
	}

	public boolean isUnbreakable() {
		return (this.unbreakable != null && this.unbreakable.booleanValue());
	}

	public void setUnbreakable(boolean unbreakable) {
		this.unbreakable = Boolean.valueOf(unbreakable);
	}
}