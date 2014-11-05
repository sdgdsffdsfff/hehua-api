package com.hehua.item.service;

import com.hehua.item.domain.ItemProperty;
import com.hehua.item.domain.PropertyValue;

import java.util.Comparator;

/**
 * Created by hewenjerry on 14-8-22.
 */
public class PropertyValueComparator implements Comparator<PropertyValue> {
    @Override
    public int compare(PropertyValue propertyValue, PropertyValue propertyValue2) {
        return propertyValue.getSort() - propertyValue2.getSort();
    }
}
