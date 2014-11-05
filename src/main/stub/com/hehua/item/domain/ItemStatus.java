/*
 * 由系统于2014-08-08 01:02:14生成。
 */

package com.hehua.item.domain;

import com.google.common.base.Function;

public class ItemStatus extends ItemStatusI {

    public static Function<ItemStatus, Integer> itemIdExtractor = new Function<ItemStatus, Integer>() {

        @Override
        public Integer apply(ItemStatus input) {
            return (int) input.getItemid();
        }
    };
}
