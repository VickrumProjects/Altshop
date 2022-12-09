package me.vickrum.alts.cmd;

import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import me.vickrum.alts.Main;
import me.vickrum.alts.entity.FactionAltsColl;
import me.vickrum.alts.entity.MConf;
import me.vickrum.alts.inventory.AltShopGui;

public class CmdAltShop extends MassiveCommand {
	private static final CmdAltShop i = new CmdAltShop();

	public static CmdAltShop get() {
		return i;
	}

	public CmdAltShop() {
		addRequirements(RequirementHasPerm.get("altshop.open"));
		setAliases(MConf.get().altshopAlias);
		setDesc("Opens up the alt shop gui");

//		addChild(new CmdAltShopSetAlts());
//		addChild(new CmdAltShopDespawnAlts());
		addChild(new MassiveCommandVersion(Main.get()).setAliases("v", "version"));
	}

	@SuppressWarnings("deprecation")
	public void perform() throws MassiveException {
		MPlayer fplayer = MPlayer.get(me);

		if (fplayer.getFaction().isNone() || fplayer.getFaction() == null) {
			if (!MConf.get().msgYouNeedAFaction.isEmpty())
				msg(MConf.get().msgYouNeedAFaction);
			return;
		}

		if (fplayer.getFaction().isSystemFaction() && MConf.get().preventFactionSystem) {
			if (!MConf.get().msgFactionIsSystem.isEmpty())
				msg(MConf.get().msgFactionIsSystem);
			return;
		}

		if (FactionAltsColl.get().get(fplayer.getFactionId()) == null) {
			FactionAltsColl.get().create(fplayer.getFactionId());
		}

		if (FactionAltsColl.get().get(fplayer.getFactionId()) != null) {
			if (fplayer.getRole().name().equalsIgnoreCase("COLEADER") || fplayer.getRole().name().equalsIgnoreCase("LEADER")) {
				new AltShopGui(this.me, fplayer.getFactionId()).open();
				return;
			} else {
				msg(MConf.get().msgNeedCoOrLeader);
				return;
			}
		}
	}

}
