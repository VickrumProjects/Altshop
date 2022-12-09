package me.vickrum.alts.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AltSpawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private final String action;

	private final String altnumber;

	private final int slot;

	public AltSpawnEvent(Player player, String action, String altnumber, int slot) {
		this.player = player;
		this.action = action;
		this.altnumber = altnumber;
		this.slot = slot;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getAction() {
		return this.action;
	}

	public String getAltNumber() {
		return this.altnumber;
	}

	public int getSlot() {
		return this.slot;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
