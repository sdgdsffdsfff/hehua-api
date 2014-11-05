/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class Item extends ItemI {

    public static final int STATUS_OK = 1;

    public static final int STATUS_DELETED = 0;

    public static Function<Item, Integer> idExtractor = new Function<Item, Integer>() {

        @Override
        public Integer apply(Item input) {
            return (int) input.getId();
        }
    };

    public static Function<Item, Integer> flashIdExtractor = new Function<Item, Integer>() {

        @Override
        public Integer apply(Item input) {
            return input.getFlashid();
        }
    };

    public boolean isOnline() {
        return status == 1;
    }

    private int quantity;

    @Override
    public String toString() {
        return "Item [id=" + id + ", cts=" + cts + ", uts=" + uts + ", name=" + name
                + ", originprice=" + originprice + ", realprice=" + realprice + ", image=" + image
                + ", images=" + images + ", sales=" + sales + ", units=" + units + ", desc=" + desc
                + ", brandid=" + brandid + ", purchaseid=" + purchaseid + ", crowedid=" + crowedid
                + ", warehouseid=" + warehouseid + ", genderid=" + genderid + ", materialid="
                + materialid + ", flashid=" + flashid + "]";
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
