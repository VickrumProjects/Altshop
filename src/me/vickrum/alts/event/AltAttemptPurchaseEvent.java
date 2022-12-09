package me.vickrum.alts.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AltAttemptPurchaseEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;

	private final String altnumber;

	private final double cost;

	public AltAttemptPurchaseEvent(Player player, String altnumber, double cost) {
		this.player = player;
		this.altnumber = altnumber;
		this.cost = cost;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getAltNumber() {
		return this.altnumber;
	}

	public double getAltCost() {
		return this.cost;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public HandlerList getHandlers() {
		return handlers;
	}
}
