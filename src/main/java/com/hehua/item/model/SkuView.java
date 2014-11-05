package com.hehua.item.model;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-20
 */
public class SkuView {
    private long pvid1;
    private long pvid2;
    private int quantity;

    public long getPvid1() {
        return pvid1;
    }

    public void setPvid1(long pvid1) {
        this.pvid1 = pvid1;
    }

    public long getPvid2() {
        return pvid2;
    }

    public void setPvid2(long pvid2) {
        this.pvid2 = pvid2;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
