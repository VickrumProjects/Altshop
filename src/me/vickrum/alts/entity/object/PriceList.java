package me.vickrum.alts.entity.object;

import com.massivecraft.massivecore.store.EntityInternal;

import java.util.List;

public class PriceList extends EntityInternal<PriceList> {
    private List<Price> price;

    public PriceList(List<Price> price) {
        this.price = price;
    }

    public List<Price> getPrice() {
        return this.price;
    }
}

