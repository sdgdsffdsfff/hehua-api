/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

import java.util.List;

public class ItemSku extends ItemSkuI {
    private List<PropertyValue> propertyValueList;

    public static Function<ItemSku, Integer> itemIdExtractor = new Function<ItemSku, Integer>() {

        @Override
        public Integer apply(ItemSku input) {
            return (int) input.getItemid();
        }
    };

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public void setPropertyValueList(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }
}
