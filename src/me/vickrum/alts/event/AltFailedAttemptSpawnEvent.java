package me.vickrum.alts.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AltFailedAttemptSpawnEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private final String action;

	public AltFailedAttemptSpawnEvent(Player player, String action) {
		this.player = player;
		this.action = action;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getAction() {
		return this.action;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
