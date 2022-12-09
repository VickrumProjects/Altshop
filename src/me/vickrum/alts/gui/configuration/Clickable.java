package me.vickrum.alts.gui.configuration;

import me.vickrum.alts.extra.ItemStackWrapper;

public class Clickable {
	private final ItemStackWrapper displayItem;

	private final String action;

	private final int slot;

	private final String texture;

	public Clickable(int slot, ItemStackWrapper itemStackWrapper) {
		this(slot, itemStackWrapper, null, null);
	}

	public Clickable(int slot, ItemStackWrapper itemStackWrapper, String texture) {
		this(slot, itemStackWrapper, null, texture);
	}

	public Clickable(int slot, ItemStackWrapper itemStackWrapper, String action, String texture) {
		this.slot = slot;
		this.displayItem = itemStackWrapper;
		this.action = action;
		this.texture = texture;
	}

	public int getSlot() {
		return this.slot;
	}

	public ItemStackWrapper getDisplayItem() {
		return this.displayItem;
	}

	public String getAction() {
		return this.action;
	}

	public String getTexture() {
		return this.texture;
	}
}