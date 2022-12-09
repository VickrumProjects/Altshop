package me.vickrum.alts.entity.object;

import com.massivecraft.massivecore.store.EntityInternal;

public class Price extends EntityInternal<Price> {

    public String name;

    public double amount;

    public Price(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
