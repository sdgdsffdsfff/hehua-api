/*
 * 由系统于2014-08-18 23:58:24生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class PropertyValue extends PropertyValueI implements Comparable<PropertyValue> {

    public static final Function<PropertyValue, Integer> idExtractor = new Function<PropertyValue, Integer>() {

        @Override
        public Integer apply(PropertyValue input) {
            return input.getId();
        }
    };

    @Override
    public int compareTo(PropertyValue o) {
        return Integer.compare(this.sort, o.sort);
    }

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
