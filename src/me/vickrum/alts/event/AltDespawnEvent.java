package me.vickrum.alts.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AltDespawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private final String action;

	private final String altnumber;

	public AltDespawnEvent(Player player, String action, String altnumber) {
		this.player = player;
		this.action = action;
		this.altnumber = altnumber;
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

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
