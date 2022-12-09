package me.vickrum.alts.engine;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;

import me.vickrum.alts.Main;
import me.vickrum.alts.entity.FactionAlts;
import me.vickrum.alts.entity.FactionAltsColl;
import me.vickrum.alts.entity.object.Alts;

public class EngineFaction extends Engine {

	private static final EngineFaction i = new EngineFaction();

	public static EngineFaction get() {
		return i;
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void factionDisband(EventFactionsDisband event) {
		if (FactionAltsColl.get().get(event.getFaction().getId()) != null) {
			FactionAlts sandy = FactionAlts.get(event.getFaction().getId());
			if (sandy.getAltsSize() >= 1) {
				for (Alts sandbot : sandy.getAlts().values()) {
					sandy.despawnAlts(sandbot);
				}
			}
			FactionAltsColl.get().get(event.getFaction().getId()).detach();
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void factionCreate(EventFactionsCreate event) {
		Bukkit.getScheduler().runTaskLater(Main.get(), new Runnable() {
			@Override
			public void run() {
				if (FactionAltsColl.get().get(event.getFactionId()) == null) {
					FactionAltsColl.get().create(event.getFactionId());
				}
			}
		}, 50L);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onLandClaim(EventFactionsChunksChange event) {
		event.getChunks().stream().forEach(loc -> {
			Faction factionAt = BoardColl.get().getFactionAt(loc);
			FactionAlts sandy = FactionAlts.get(factionAt.getId());
			if (sandy != null) {
				for (Alts sandbot : sandy.getAlts().values()) {
					PS chunkPs = sandbot.getPs().getChunk(true);
					if (Objects.equals(loc.getChunkX(), chunkPs.getChunkX())
							&& Objects.equals(loc.getChunkZ(), chunkPs.getChunkZ())) {
						sandy.despawnAlts(sandbot);
						// factionAt.msg(MConf.get().msgBotDespawnedSinceClaim);
						break;
					}
				}
			}
		});
	}

	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		Entity[] entities;
		for (int length = (entities = event.getChunk().getEntities()).length, i = 0; i < length; ++i) {
			PS loc = PS.valueOf(event.getWorld().getName(), event.getChunk().getX(),
					event.getChunk().getZ());
			Faction factionAt = BoardColl.get().getFactionAt(loc);
			FactionAlts sandy = FactionAlts.get(factionAt.getId());
			if (factionAt == null || FactionAlts.get(factionAt.getId()) == null)
				return;
			if (sandy.isAlts(entities[i])) {
				event.setCancelled(true);
				event.getChunk().load();
				break;
			}
		}
	}

//	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
//	public void onPlayerInteractAtEntity(NPCLeftClickEvent event) {
//		Player clicker = event.getClicker();
//		NPC npc = event.getNPC();
//		if (npc.getName().contains(MConf.get().sandbotName)) {
//			if (!MConf.get().punchBreaks)
//				return;
//			FLocation flocation = new FLocation(npc.getEntity().getLocation().getWorld().getName(),
//					npc.getEntity().getLocation().getChunk().getX(), npc.getEntity().getLocation().getChunk().getZ());
//			Faction factionAt = Board.getInstance().getFactionAt(flocation);
//			FactionSandBot sandy = FactionSandBot.get(factionAt.getId());
//
//			int indexOfSandbot = 0;
//
//			Sandbot sandbotToRemove = null;
//
//			for (Sandbot sandbot : sandy.getSandbots().values()) {
//				indexOfSandbot++;
//				if (npc.getUniqueId().equals(sandbot.getUniqueId())) {
//					sandbotToRemove = sandbot;
//					break;
//				}
//			}
//
//			if (sandbotToRemove == null) {
//				return;
//			}
//
//			sandy.despawnSandbot(sandbotToRemove);
//			MixinMessage.get().msgOne(clicker, MConf.get().msgBotDespawnedMsg.replace("{number}",
//					NumberFormat.getInstance().format(indexOfSandbot)));
//		}
//	}
}
