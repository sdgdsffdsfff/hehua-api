package com.hehua.item.model;

import com.google.common.base.Function;
import com.hehua.item.domain.Flash;

/**
 * Created by liuweiwei on 14-9-13.
 */
public class FlashEntry {

    private int preCrow;
    private int crowd;
    private Flash flash;

    public static Function<FlashEntry, Integer> itemIdExtractor = new Function<FlashEntry, Integer>() {

        @Override
        public Integer apply(FlashEntry input) {
            if (input.getFlash().isGroup()) {
                return null;
            }
            return input.getFlash().getItemid();
        }
    };

    public int getPreCrow() {
        return preCrow;
    }

    public void setPreCrow(int preCrow) {
        this.preCrow = preCrow;
    }

    public int getCrowd() {
        return crowd;
    }

    public void setCrowd(int crowd) {
        this.crowd = crowd;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }
}
