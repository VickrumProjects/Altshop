package me.vickrum.alts.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CategorySelectEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private final String inventory;

	public CategorySelectEvent(Player p, String inventory) {
		this.player = p;
		this.inventory = inventory;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getInventory() {
		return this.inventory;
	}
}
