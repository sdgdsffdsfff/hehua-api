package com.hehua.item.model;

import java.util.List;

/**
 * Created by liuweiwei on 14-9-13.
 */
public class FlashEntryList {
    private int population;
    private List<FlashEntry> flashEntries;

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public List<FlashEntry> getFlashEntries() {
        return flashEntries;
    }

    public void setFlashEntries(List<FlashEntry> flashEntries) {
        this.flashEntries = flashEntries;
    }
}
