package me.vickrum.alts.entity.object;

import java.util.UUID;

import org.bukkit.Location;

import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.EntityInternal;

public class Alts extends EntityInternal<Alts> {
	private String uuid;
	private PS loc;
	private Boolean despawned;

	public Alts() {
		this.uuid = null;
		this.loc = null;
		this.despawned = true;
	}

	public Alts(String uuid, Location location) {
		this.uuid = uuid;
		this.loc = PS.valueOf(location);
		this.despawned = true;
	}

	public PS getPs() {
		return loc;
	}

	public void setDespawned(Boolean despawned) {
		this.despawned = despawned;
		this.changed();
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		this.changed();
	}

	public void setLoc(PS ps) {
		this.loc = ps;
		this.changed();
	}

	public UUID getUniqueId() {
		return uuid == null ? null : UUID.fromString(uuid);
	}

	public Location getLocation() {
		return loc.asBukkitLocation(true);
	}

	public Boolean isDespawned() {
		return despawned;
	}
}