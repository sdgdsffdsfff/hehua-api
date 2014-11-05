/*
 * 由系统于2014-08-21 22:53:55生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.Date;

public class ItemSkuI {

    protected long id;

    protected Date cts;

    protected Date uts;

    protected long itemid;

    protected double originprice;

    protected double realprice;

    protected int quantity;

    protected String pvids;

    protected int status;

    protected String barcode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCts() {
        return cts;
    }

    public void setCts(Date cts) {
        this.cts = cts;
    }

    public Date getUts() {
        return uts;
    }

    public void setUts(Date uts) {
        this.uts = uts;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public double getOriginprice() {
        return originprice;
    }

    public void setOriginprice(double originprice) {
        this.originprice = originprice;
    }

    public double getRealprice() {
        return realprice;
    }

    public void setRealprice(double realprice) {
        this.realprice = realprice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public String getPvids() {
        return pvids;
    }

    public void setPvids(String pvids) {
        this.pvids = pvids;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
