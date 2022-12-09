package me.vickrum.alts.engine;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.util.Txt;
import me.vickrum.alts.Main;
import me.vickrum.alts.entity.FactionAlts;
import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.entity.object.Alts;
import me.vickrum.alts.event.*;
import me.vickrum.alts.inventory.ManageAltShopGui;
import me.vickrum.alts.inventory.ViewAltShopGui;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class EngineNewAltsGui extends Engine {

	private static EngineNewAltsGui i = new EngineNewAltsGui();

	public static EngineNewAltsGui get() {
		return i;
	}
	
//	@EventHandler
//	public void onCommandPreprocessEventFixAll3(PlayerCommandPreprocessEvent event) {
//		String command = event.getMessage().toLowerCase();
//		
//		if (!command.equalsIgnoreCase("/f alts"))
//			return;
//		
//		if (!event.getPlayer().hasPermission("factions.alts")) {
//			event.getPlayer().sendMessage(Txt.parse("&cNo Permission."));
//			return;
//		}
//		
//		event.setCancelled(true);
//		event.getPlayer().chat("/" + MConf.get().altshopAlias.get(0));
//		return;
//	}
//	
//	@EventHandler
//	public void onCommandPreprocessEventFixAll(PlayerCommandPreprocessEvent event) {
//		String command = event.getMessage().toLowerCase();
//		
//		if (!command.equalsIgnoreCase("/f alt"))
//			return;
//		
//		if (!event.getPlayer().hasPermission("factions.alts")) {
//			event.getPlayer().sendMessage(Txt.parse("&cNo Permission."));
//			return;
//		}
//		
//		event.setCancelled(true);
//		event.getPlayer().chat("/" + MConf.get().altshopAlias.get(0));
//		return;
//	}
	
//	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//	public void onLandClaim(LandUnclaimEvent event) {
//		MPlayer mplayer = MPlayer.get(event.getfPlayer().getId());
//		
//		for (Alts sandbot : mplayer.getAltBots().values()) {
//			PS chunkPs = sandbot.getPs().getChunk(true);
//			if (Objects.equals(event.getLocation().getChunk().getX(), chunkPs.getChunkX())
//					&& Objects.equals(event.getLocation().getChunk().getZ(), chunkPs.getChunkZ())) {
//				mplayer.despawnAltBot(sandbot);
//				break;
//			}
//		}
//	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerTpAlt(final AltInformationEvent event) {
		MPlayer fplayer = MPlayer.get(event.getPlayer().getUniqueId());
		FactionAlts sandy = FactionAlts.get(fplayer.getFactionId());
		Alts altbot = sandy.getAlts().get("ALT:" + event.getAltNumber());
		
		if (altbot == null || altbot.isDespawned()) {
			fplayer.msg(MConf.get().msgBotDontTpExistMsg);
			return;
		}

		Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(event.getPlayer().getLocation()));

		if (fplayer.getFaction().isNone() || fplayer.getFaction() == null) {
			fplayer.msg(MConf.get().msgYouNeedAFaction);
			return;
		}

		if (factionAt.isSystemFaction()) {
			fplayer.msg(MConf.get().msgCantSpawnInWilderness);
			return;
		}

		if (factionAt.isNone() && MConf.get().allowInWilderness == false) {
			fplayer.msg(MConf.get().msgCantSpawnInWilderness);
			return;
		}

		if (MConf.get().checkRadiusForMaterial) {

			for(Block b : getNearbyBlocks(event.getPlayer().getLocation(), MConf.get().checkForMaterialsRadius)) {
				if (MConf.get().checkForMaterials.contains(b.getType().name())) {
					PS psAt = PS.valueOf(event.getPlayer().getLocation());
					sandy.spawnAlts(altbot, psAt, event.getAltNumber());

					fplayer.msg(MConf.get().msgBotTelportedToLocation.replace("{number}", event.getAltNumber()));
					return;
				}
			}

			if (!MConf.get().msgDoesntIncludeMaterial.isEmpty())
				fplayer.msg(MConf.get().msgDoesntIncludeMaterial);

			return;
		} else {
			PS psAt = PS.valueOf(event.getPlayer().getLocation());
			sandy.spawnAlts(altbot, psAt, event.getAltNumber());

			fplayer.msg(MConf.get().msgBotTelportedToLocation.replace("{number}", event.getAltNumber()));

			return;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerAltSpawn(final AltSpawnEvent event) {
		MPlayer fplayer = MPlayer.get(event.getPlayer().getUniqueId());
		FactionAlts sandy = FactionAlts.get(fplayer.getFactionId());
		Alts altbot = sandy.getAlts().get("ALT:" + event.getAltNumber());
		
		if (altbot == null) {
			fplayer.msg(MConf.get().msgPurchaseAltBeforeYouUse.replace("{number}", event.getAltNumber()));
			return;
		}

		if (!altbot.isDespawned()) {
			fplayer.msg(MConf.get().msgBotAlreadySpawned);
			return;
		}
		
		Faction factionAt = BoardColl.get().getFactionAt(PS.valueOf(event.getPlayer().getLocation()));

		if (fplayer.getFaction().isNone() || fplayer.getFaction() == null) {
			fplayer.msg(MConf.get().msgYouNeedAFaction);
			return;
		}

		if (factionAt.isSystemFaction()) {
			fplayer.msg(MConf.get().msgCantSpawnInWilderness);
			return;
		}

		if (factionAt.isNone() && MConf.get().allowInWilderness == false) {
			fplayer.msg(MConf.get().msgCantSpawnInWilderness);
			return;
		}

		if (MConf.get().checkRadiusForMaterial) {

			for(Block b : getNearbyBlocks(event.getPlayer().getLocation(), MConf.get().checkForMaterialsRadius)) {
				if (MConf.get().checkForMaterials.contains(b.getType().name())) {
					PS psAt = PS.valueOf(event.getPlayer().getLocation());
					sandy.spawnAlts(altbot, psAt, event.getAltNumber());

					fplayer.msg(MConf.get().spawnedAltSpawnedMsg.replace("{number}", event.getAltNumber()));
					return;
				}
			}

			if (!MConf.get().msgDoesntIncludeMaterial.isEmpty())
				fplayer.msg(MConf.get().msgDoesntIncludeMaterial);

			return;
		} else {
			PS psAt = PS.valueOf(event.getPlayer().getLocation());
			sandy.spawnAlts(altbot, psAt, event.getAltNumber());

			fplayer.msg(MConf.get().spawnedAltSpawnedMsg.replace("{number}", event.getAltNumber()));
		}
		return;
	}

	public static List<Block> getNearbyBlocks(Location location, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
			for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
				for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
					Block block = new Location(location.getWorld(), x, y , z).getBlock();
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerAltDeSpawn(final AltDespawnEvent event) {
		MPlayer fplayer = MPlayer.get(event.getPlayer().getUniqueId());
		FactionAlts sandy = FactionAlts.get(fplayer.getFactionId());
		Alts altbot = sandy.getAlts().get("ALT:" + event.getAltNumber());
		
		if (altbot == null || altbot.isDespawned()) {
			fplayer.msg(MConf.get().msgBotDontExistMsg);
			return;
		}

		sandy.despawnAlts(altbot);
		fplayer.msg(MConf.get().msgBotDespawnedMsg.replace("{number}", event.getAltNumber()));
		return;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void inventorySelectedMain(final CategorySelectEvent e) {
		MPlayer fplayer = MPlayer.get(e.getPlayer().getUniqueId());
		FactionAlts sandy = FactionAlts.get(fplayer.getFactionId());
		
		if (e.getInventory().equalsIgnoreCase("VIEW_ALTS")) {
			if (sandy.getAltsSize() == 0) {
				if (!MConf.get().msgNoAltsToManage.isEmpty())
					fplayer.msg(MConf.get().msgNoAltsToManage);
				return;
			}

			new ViewAltShopGui(e.getPlayer(), fplayer.getFactionId()).open();
			return;
		} else if (e.getInventory().equalsIgnoreCase("MANAGE_ALTS")) {
			if (sandy.getAltsSize() == 0) {
				if (!MConf.get().msgNoAltsToManage.isEmpty())
					fplayer.msg(MConf.get().msgNoAltsToManage);
				return;
			}

			new ManageAltShopGui(e.getPlayer(), fplayer.getFactionId()).open();
			return;
		} else if (e.getInventory().equalsIgnoreCase("PURCHASE_ALTS")) {
			if (sandy.getAltsSize() < MConf.get().maxAltSize) {
				int alt = sandy.getAltsSize() + 1;
				Double PriceforAlt = Main.get().getPrice("ALT_" + alt).getAmount();

				AltAttemptPurchaseEvent confClicked = new AltAttemptPurchaseEvent(e.getPlayer(),
						String.valueOf(sandy.getAltsSize() + 1), PriceforAlt);
				Bukkit.getPluginManager().callEvent(confClicked);
			} else {
				if (!MConf.get().msgAlreadyHaveMaxAltSize.isEmpty())
					fplayer.msg(MConf.get().msgAlreadyHaveMaxAltSize.replace("{max}",
							NumberFormat.getInstance().format(MConf.get().maxAltSize)));
				return;
			}
		}

		return;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerAttemptPurchase(final AltAttemptPurchaseEvent event) {
		MPlayer fplayer = MPlayer.get(event.getPlayer().getUniqueId());
		FactionAlts sandy = FactionAlts.get(fplayer.getFactionId());
		
		if (sandy.getAltsSize() + 1 > MConf.get().maxAltSize) {
			if (!MConf.get().msgAlreadyHaveMaxAltSize.isEmpty())
				fplayer.msg(MConf.get().msgAlreadyHaveMaxAltSize.replace("{max}",
						NumberFormat.getInstance().format(MConf.get().maxAltSize)));
			return;
		}

		Economy economy = Main.getEconomy();
		EconomyResponse r = economy.withdrawPlayer((OfflinePlayer) event.getPlayer(), event.getAltCost());

		if (r.transactionSuccess()) {
			fplayer.msg(MConf.get().msgSuccessfullyPurchasedAlt.replace("{number}", event.getAltNumber()));
			sandy.addAltsSize(1);
			fplayer.getPlayer().closeInventory();
			return;
		} else {
			fplayer.msg(MConf.get().msgNotEnoughMoney
					.replace("{amount}", NumberFormat.getInstance().format(event.getAltCost()))
					.replace("{number}", event.getAltNumber()));
			return;
		}
	}
}
