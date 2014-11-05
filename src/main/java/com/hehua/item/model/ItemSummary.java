package com.hehua.item.model;

import java.util.List;

import com.hehua.item.domain.Flash;
import com.hehua.item.domain.ItemRecommend;
import com.hehua.item.domain.ItemSku;

/**
 * @author YaoZhidong
 * @version 1.0
 * @created 14-8-10
 */
public class ItemSummary {

    private ItemLite item;

    private ItemRecommend itemRecommend;

    private List<ItemSku> itemSkus;

    private Flash flash;

    public ItemLite getItem() {
        return item;
    }

    public void setItem(ItemLite item) {
        this.item = item;
    }

    public ItemRecommend getItemRecommend() {
        return itemRecommend;
    }

    public void setItemRecommend(ItemRecommend itemRecommend) {
        this.itemRecommend = itemRecommend;
    }

    public List<ItemSku> getItemSkus() {
        return itemSkus;
    }

    public void setItemSkus(List<ItemSku> itemSkus) {
        this.itemSkus = itemSkus;
    }

    /**
     * 通过SKU判断是否卖光
     *
     * @param itemSkus
     * @return
     */
    public boolean isSoldOut() {
        boolean soldOut = true;
        if (itemSkus != null) {
            for (ItemSku sku : itemSkus) {
                if (sku.getQuantity() > 0) {
                    soldOut = false;
                    break;
                }
            }
        }
        return soldOut;
    }

    public Flash getFlash() {
        return flash;
    }

    public void setFlash(Flash flash) {
        this.flash = flash;
    }

}
