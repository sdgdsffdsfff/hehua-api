/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;
import com.hehua.item.domain.enums.FreeFlashEnums;
import com.hehua.item.model.FreeFlashList;
import com.hehua.item.model.ItemLite;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FreeFlash implements Comparable<FreeFlash>{

    public static Function<FreeFlash, Integer> itemIdExtractor = new Function<FreeFlash, Integer>() {

        @Override
        public Integer apply(FreeFlash input) {
            return input.getItemid();
        }
    };

    private int id;

    private Date onlinedate;

    private int itemid;

    private int skuid;

    private int applynum;

    private int freequantity;

    private Date starttime;

    private Date endtime;

    private int status;

    private int priority;

    private Date createtime;

    private ItemLite itemLite;

    private Item item;

    private boolean isApply = false;//判断登录用户是否是申请过该众测商品

    private List<ItemProperty> itemProperties;

    private FreeFlashEnums freeFlashEnums;
    /**
     * 是否在线
     * @return
     */
    public boolean isOnline() {
        return this.status == 1;
    }

    /**
     * 是否已申请完
     * @return
     */
    public boolean isSoldout() {
        //可以无限卖
        return false;
        //return this.applynum == this.freequantity;
    }

    @Override
    public int compareTo(FreeFlash o) {
        return Integer.compare(this.priority, o.priority);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOnlinedate() {
        return onlinedate;
    }

    public void setOnlinedate(Date onlinedate) {
        this.onlinedate = onlinedate;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getSkuid() {
        return skuid;
    }

    public void setSkuid(int skuid) {
        this.skuid = skuid;
    }

    public int getApplynum() {
        return applynum;
    }

    public void setApplynum(int applynum) {
        this.applynum = applynum;
    }

    public int getFreequantity() {
        return freequantity;
    }

    public void setFreequantity(int freequantity) {
        this.freequantity = freequantity;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public ItemLite getItemLite() {
        return itemLite;
    }

    public void setItemLite(ItemLite itemLite) {
        this.itemLite = itemLite;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(List<ItemProperty> itemProperties) {
        this.itemProperties = itemProperties;
    }

    public FreeFlashEnums getFreeFlashEnums() {
        return freeFlashEnums;
    }

    public void setFreeFlashEnums(FreeFlashEnums freeFlashEnums) {
        this.freeFlashEnums = freeFlashEnums;
    }
    /**
     * 当期众测商品闪购是否 在闪购范围内
     *
     * @return
     */
    public boolean isSessionRange() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        long currentTime = calendar.getTime().getTime();

        return currentTime >= this.starttime.getTime() &&  currentTime <= this.endtime.getTime();
    }

    public boolean isApply() {
        return isApply;
    }

    public void setApply(boolean isApply) {
        this.isApply = isApply;
    }
}
