package me.vickrum.alts.entity;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.store.Coll;

public class FactionAltsColl extends Coll<FactionAlts> {

	private static final FactionAltsColl i = new FactionAltsColl();

	public static FactionAltsColl get() {
		return i;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (!active)
			return;
		FactionAlts.i = this.get(MassiveCore.INSTANCE, true);
	}

	@Override
	public String fixId(Object oid) {
		if (oid instanceof FactionAlts)
			return ((FactionAlts) oid).getId();
		if (oid instanceof String)
			return (String) oid;

		return null;
	}
}
