package me.vickrum.alts.entity;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.store.Entity;

import me.vickrum.alts.CoreParticipator;
import me.vickrum.alts.entity.object.Alts;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.trait.Gravity;

public class FactionAlts extends Entity<FactionAlts> implements CoreParticipator {

	protected static transient FactionAlts i;

	public int altsSize = 0;

	public static FactionAlts get(Object object) {
		return FactionAltsColl.get().get(object);
	}

	// -------------------------------------------- //
	// OVERRIDE: ENTITY
	// -------------------------------------------- //
	// The actual faction id looks something like
	// "54947df8-0e9e-4471-a2f9-9af509fb5889" and that is not too easy to remember
	// for humans.
	// Thus we make use of a name. Since the id is used in all foreign key
	// situations changing the name is fine.
	// Null should never happen. The name must not be null.
	private String name = null;

	@Override
	public FactionAlts load(FactionAlts that) {
		super.load(that);
		this.setAltsSize(this.altsSize);
		this.setAltss(that.alts);
		return this;
	}

	// -------------------------------------------- //
	// Total
	// -------------------------------------------- //

	public int getAltsSize() {
		return altsSize;
	}

	public void setAltsSize(int amount) {
		this.altsSize = amount;
		this.changed();
	}

	public void addAltsSize(int amount) {
		this.altsSize = altsSize + amount;
		this.changed();
	}

	public void removeAltsSize(int amount) {
		double newAmount = this.getAltsSize() - amount;

		if (newAmount <= 0) {
			this.altsSize = 0;
			this.changed();
			return;
		}

		this.altsSize = altsSize - amount;
		changed();
	}

	public boolean hasAltsSize(int amount) {
		return getAltsSize() >= amount;
	}
	// -------------------------------------------- //
	// Alts
	// -------------------------------------------- //

	private MassiveMap<String, Alts> alts = new MassiveMap<>();

	public MassiveMap<String, Alts> getAlts() {
		return alts;
	}

	public void setAltss(MassiveMap<String, Alts> alts) {
		this.alts = alts;
		this.changed();
	}

	public void addAlts(String slot, Alts sandbot) {
		this.alts.put(slot, sandbot);
		this.changed();
	}

	public void despawnAlts(Alts sandbot) {
		NPC byUniqueId = CitizensAPI.getNPCRegistry().getByUniqueId(sandbot.getUniqueId());

		if (byUniqueId != null) {
			byUniqueId.despawn();
			byUniqueId.destroy();
		}

		sandbot.setUuid(null);
		sandbot.setDespawned(true);
		this.changed();
	}

	public void spawnAlts(Alts sandbot, PS ps, String number) {
		if (sandbot.getUniqueId() != null || sandbot.getPs() != null || !sandbot.isDespawned()) {
			despawnAlts(sandbot);
		}

		Location npcLocation = ps.asBukkitLocation().clone();
		Faction faction = Faction.get(this.getId());

		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER,
				MConf.get().altName.replace("{player}", faction.getName()).replace("{number}", number));
		npc.data().set("removefromplayerlist", false);
		npc.setFlyable(true);
		npc.setProtected(true);
		Gravity gravity = CitizensAPI.getTraitFactory().getTrait("gravity");
		gravity.gravitate(true);
		npc.addTrait(gravity);
		Owner owner = CitizensAPI.getTraitFactory().getTrait("owner");
		owner.setOwner(faction.getLeader().getName(), UUID.fromString(faction.getLeader().getId()));
		npc.addTrait(owner);
		npc.data().setPersistent("removefromplayerlist", false);
		npc.setProtected(true);
		npc.data().set("player-skin-name", MConf.get().altSkinName);
		npc.spawn(npcLocation);
		npc.getEntity().setVelocity(npc.getEntity().getVelocity().add(new Vector(0.0, 0.0, 0.0)));
		sandbot.setDespawned(false);
		sandbot.setUuid(npc.getUniqueId().toString());
		sandbot.setLoc(ps);

//		NPC npc1 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "DrePvP");
//		NPC npc2 = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "KingpinJordan");
//
//		npc1.data().set("player-skin-name", "DrePvP");
//		npc1.spawn(npcLocation);
//
//		npc2.data().set("player-skin-name", "KingpinJordan");
//		npc2.spawn(npcLocation);
//
//		npc1.getEntity().setPassenger(npc2.getEntity());

		this.changed();
	}

	public boolean isAlts(org.bukkit.entity.Entity entity) {
		NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);

		if (npc == null)
			return false;

		for (Alts sandbot : getAlts().values()) {
			if (npc.getUniqueId().equals(sandbot.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	public Alts getAlts(UUID uuid) {
		for (Alts sandbot : getAlts().values()) {
			if (sandbot.getUniqueId().equals(uuid)) {
				return sandbot;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		String ret = this.name;

		if (MConf.get().factionNameForceUpperCase) {
			ret = ret.toUpperCase();
		}

		return ret;
	}
}
