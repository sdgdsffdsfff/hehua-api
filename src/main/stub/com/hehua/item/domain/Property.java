/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.domain;

import java.util.List;

import com.google.common.base.Function;

public class Property extends PropertyI implements Comparable<Property> {

    public static final Function<Property, Integer> idExtractor = new Function<Property, Integer>() {

        @Override
        public Integer apply(Property input) {
            return input.getId();
        }
    };

    @Override
    public int compareTo(Property o) {
        return Integer.compare(this.sort, o.sort);
    }

    private List<PropertyValue> values;

    public List<PropertyValue> getValues() {
        return values;
    }

    public void setValues(List<PropertyValue> values) {
        this.values = values;
    }

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
