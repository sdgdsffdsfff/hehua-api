/*
 * 由系统于2014-08-21 22:53:54生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.Date;

public class ItemDetailI {

    protected long id;

    protected Date cts;

    protected Date uts;

    protected long itemid;

    protected long brandid;

    protected long image;

    protected String images;

    protected String desc;

    protected long purchaseid;

    protected long warehouseid;

    private int genderid;

    private String crowedid;

    private int materialid;

    private String meta;

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

    public long getBrandid() {
        return brandid;
    }

    public void setBrandid(long brandid) {
        this.brandid = brandid;
    }

    public long getImage() {
        return image;
    }

    public void setImage(long image) {
        this.image = image;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(long purchaseid) {
        this.purchaseid = purchaseid;
    }

    public long getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(long warehouseid) {
        this.warehouseid = warehouseid;
    }

    public int getGenderid() {
        return genderid;
    }

    public void setGenderid(int genderid) {
        this.genderid = genderid;
    }

    public String getCrowedid() {
        return crowedid;
    }

    public void setCrowedid(String crowedid) {
        this.crowedid = crowedid;
    }

    public int getMaterialid() {
        return materialid;
    }

    public void setMaterialid(int materialid) {
        this.materialid = materialid;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}
