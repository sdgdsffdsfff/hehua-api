package com.hehua.item.service;

import com.hehua.item.domain.ItemProperty;

import java.util.Comparator;

/**
 * Created by hewenjerry on 14-8-22.
 */
public class ItemPropertyComparator implements Comparator<ItemProperty> {
    @Override
    public int compare(ItemProperty itemProperty, ItemProperty itemProperty2) {
        return itemProperty.getSort() - itemProperty2.getSort();
    }
}
