/*
 * 由系统于2014-08-21 23:22:05生成，请勿人为进行任何修改！
 */

package com.hehua.item.domain;

import java.util.Date;

public class ItemPropertyI {

    protected long id;

    protected Date cts;

    protected Date uts;

    protected long itemid;

    protected int pid;

    protected int pvid;

    protected long issku;

    protected long skuid;

    protected int sort;

    private String pext;

    private String pvext;

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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPvid() {
        return pvid;
    }

    public void setPvid(int pvid) {
        this.pvid = pvid;
    }

    public long getIssku() {
        return issku;
    }

    public void setIssku(long issku) {
        this.issku = issku;
    }

    public long getSkuid() {
        return skuid;
    }

    public void setSkuid(long skuid) {
        this.skuid = skuid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getPext() {
        return pext;
    }

    public void setPext(String pext) {
        this.pext = pext;
    }

    public String getPvext() {
        return pvext;
    }

    public void setPvext(String pvext) {
        this.pvext = pvext;
    }
}
